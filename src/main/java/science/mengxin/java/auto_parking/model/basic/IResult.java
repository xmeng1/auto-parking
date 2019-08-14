package science.mengxin.java.auto_parking.model.basic;

import java.io.Serializable;

public interface IResult<T> extends Serializable {

  T getResult();

  void setResult(T result);

  String getMessage();

  void setMessage(String message);

  int getError();

  void setError(int error);

  String toString();
}
