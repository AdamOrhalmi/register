package register;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SORM implements Serializable {
    private static final String IDENT_FIELD = "ident";

    private Connection connection;

    public SORM(Connection connection) {
        this.connection = connection;
    }

    public String getCreateString(Class<?> clazz) {
        return String.format("CREATE TABLE %s (\n%s\n)",
                getTableName(clazz),
                getColumnsString(clazz, f -> getColumnName(f) + " " + getSQLType(f.getType()), ",\n")
        );
    }

    public String getDropString(Class<?> clazz) {
        return String.format("DROP TABLE %s", clazz.getSimpleName());
    }

    public String getInsertString(Class<?> clazz) {
        return String.format("INSERT INTO %s (%s) VALUES (%s)",
                getTableName(clazz),
                getColumnsString(clazz, this::getColumnName),
                getColumnsString(clazz, f -> "?")
        );
    }

    public String getSelectString(Class<?> clazz) {
        return String.format("SELECT %s FROM %s",
                getColumnsString(clazz, this::getColumnName),
                getTableName(clazz));
    }

    public String getFindString(Class<?> clazz) {
        return String.format("SELECT %s FROM %s WHERE %s = ?",
                getColumnsString(clazz, this::getColumnName),
                getTableName(clazz),
                getColumnName(getIdentField(clazz))
        );
    }

    public String getTruncateString(Class<?> clazz) {
        return String.format("TRUNCATE TABLE %s", getTableName(clazz));
    }

    public String getDeleteString(Class<?> clazz) {
        return String.format("DELETE FROM %s WHERE %s = ?",
                getTableName(clazz),
                getColumnName(getIdentField(clazz))
        );
    }

    public String getUpdateString(Class<?> clazz) {
        return String.format("UPDATE %s SET %s WHERE %s = ?",
                getTableName(clazz),
                getColumnsString(clazz, f -> getColumnName(f) + " = ?"),
                getColumnName(getIdentField(clazz))
        );
    }

    public void insert(Object o) throws Exception {
        Class<?> clazz = o.getClass();
        String command = getInsertString(clazz);

        try (PreparedStatement stmt = connection.prepareStatement(command)) {
            int index = 1;
            for (Field field : clazz.getDeclaredFields()) {
                setValueOfFielToPS(o, stmt, index, field);
                index++;
            }

            stmt.executeUpdate();
        }
    }

    public void update(Object o) throws Exception {
        Class<?> clazz = o.getClass();
        String command = getUpdateString(clazz);

        try (PreparedStatement stmt = connection.prepareStatement(command)) {
            int index = 1;
            for (Field field : clazz.getDeclaredFields()) {
                setValueOfFielToPS(o, stmt, index, field);
                index++;
            }

            Field field = getIdentField(clazz);
            setValueOfFielToPS(o, stmt, index, field);

            stmt.executeUpdate();
        }
    }

    public void delete(Object o) throws Exception {
        Class<?> clazz = o.getClass();
        String command = getDeleteString(clazz);

        try (PreparedStatement stmt = connection.prepareStatement(command)) {
            Field field = getIdentField(clazz);
            setValueOfFielToPS(o, stmt, 1, field);
            stmt.executeUpdate();
        }
    }

    public void truncate(Class<?> clazz) throws Exception {
        String command = getTruncateString(clazz);

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(command);
        }
    }
    public void create(Class<?> clazz) throws Exception {
        String command = getCreateString(clazz);

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(command);
        }
    }
    public void drop(Class<?> clazz) throws Exception {
        String command = getDropString(clazz);

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(command);
        }
    }

    public <T> T find(Class<T> clazz, Object id) throws Exception {
        String command = getFindString(clazz);

        try (PreparedStatement stmt = connection.prepareStatement(command)) {
            stmt.setObject(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    T object = clazz.newInstance();
                    int index = 1;
                    for (Field field : clazz.getDeclaredFields()) {
                        setValueToFieldFromResultSet(rs, (T) object, index, field);
                        index++;
                    }
                    return object;
                } else
                    return null;
            }
        }
    }

    public <T> List<T> select(Class<T> clazz) throws Exception {
        String command = getSelectString(clazz);
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(command)) {
            List<T> result = new ArrayList<>();

            while (rs.next()) {
                T object = clazz.newInstance();
                int index = 1;
                for (Field field : clazz.getDeclaredFields()) {
                    setValueToFieldFromResultSet(rs, object, index, field);
                    index++;
                }
                result.add(object);
            }

            return result;
        }
    }

    private <T> void setValueToFieldFromResultSet(ResultSet rs, T object, int index, Field field) throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        Object value = rs.getObject(index);
        field.set(object, value);
    }

    private String getSQLType(Class<?> clazz) {
        switch (clazz.getName()) {
            case "int":
                return "INTEGER";
            case "java.lang.String":
                return "VARCHAR(64)";
        }
        throw new IllegalArgumentException("Not supported Java type " + clazz);
    }

    private String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    private String getColumnName(Field field) {
        return field.getName();
    }

    private String getColumnsString(Class<?> clazz, Function<Field, String> mapFunction, String delimiter) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(mapFunction).collect(Collectors.joining(delimiter));
    }

    private String getColumnsString(Class<?> clazz, Function<Field, String> mapFunction) {
        return getColumnsString(clazz, mapFunction, ", ");
    }

    private Field getIdentField(Class<?> clazz) {
        try {
            return clazz.getDeclaredField(IDENT_FIELD);
        } catch (NoSuchFieldException e) {
            throw new SORMException("Ident field is required", e);
        }
    }

    private void setValueOfFielToPS(Object o, PreparedStatement stmt, int index, Field field) throws IllegalAccessException, SQLException {
        field.setAccessible(true);
        Object value = field.get(o);
        stmt.setObject(index, value);
    }
}
