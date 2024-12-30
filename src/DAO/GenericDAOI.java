package DAO;

import java.io.IOException;
import java.util.List;

public interface GenericDAOI<T> {
    void add(T e);
    void delete(int id);
    void update(T e);
    List<T> display();

    
}
