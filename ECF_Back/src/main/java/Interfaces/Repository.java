package Interfaces;

import java.util.List;

public interface Repository<T> {


    public boolean create (T element);

    public boolean delete(Long id);

    public T getById(Long id);

    public List<T> getAll();

    public void close();

}
