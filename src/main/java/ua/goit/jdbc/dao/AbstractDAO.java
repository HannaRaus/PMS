package ua.goit.jdbc.dao;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T> implements GenericDAO<T> {
    private final DatabaseConnectionManager connectionManager;

    protected AbstractDAO(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    protected abstract String getCreateQuery(T object);

    protected abstract String getUpdateQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getLastIdQuery();

    protected abstract String getSelectAllQuery();

    protected abstract void setObjectStatement(PreparedStatement statement, long id, T object) throws DAOException;

    protected abstract T convertToObject(ResultSet resultSet) throws DAOException;

    @Override
    public T create(T entity) throws DAOException {
        String createQuery = getCreateQuery(entity);
        T createdEntity;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(createQuery)) {
            setObjectStatement(statement, 0, entity);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                createdEntity = read(resultSet.getInt(1));
            } else {
                throw new DAOException("Problems with creating the object");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return createdEntity;
    }

    @Override
    public T read(long id) throws DAOException {
        T entity;
        String selectByIdQuery = getSelectByIdQuery();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectByIdQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = convertToObject(resultSet);
            } else {
                throw new DAOException("There is no object with such ID!");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return entity;
    }

    @Override
    public T update(long id, T entity) throws DAOException {
        String updateQuery = getUpdateQuery();
        T updated;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            setObjectStatement(statement, id, entity);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                updated = read(resultSet.getInt(1));
            } else {
                throw new DAOException("There are problems with updating the object");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return updated;
    }

    @Override
    public void delete(long id) throws DAOException {
        String deleteQuery = getDeleteQuery();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
    }

    protected long getLastId() throws DAOException {
        String lastIdQuery = getLastIdQuery();
        long result = 0;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(lastIdQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getLong("max");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return result;
    }

    @Override
    public List<T> readAll() throws DAOException {
        List<T> entities = new ArrayList<>();
        T entity;
        String selectAllQuery = getSelectAllQuery();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectAllQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entity = convertToObject(resultSet);
                entities.add(entity);
            }
        } catch (
                SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
        return entities;
    }
}
