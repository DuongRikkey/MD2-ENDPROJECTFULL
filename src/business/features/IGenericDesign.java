package business.features;

public interface IGenericDesign <T,E> {
    void addAndUpdate(T t);
    void remove(E id);
    int findIndexbyID(E id);
    E getNewId();
}
