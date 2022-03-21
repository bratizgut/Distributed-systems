package task2.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    
    public Optional<T>getById(long id) throws SQLException;
    public List<T> getAll() throws SQLException;
    
    public int delete (T t) throws SQLException;
    public int update(T t) throws SQLException;
    
    public void saveExecute(T t) throws SQLException;
    public void savePrep(T t) throws SQLException;
    public void saveBatch(T t) throws SQLException;
}
