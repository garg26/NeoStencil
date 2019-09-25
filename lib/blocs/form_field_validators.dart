import 'dart:async';

class Validators {
  final validateEmail =
      StreamTransformer<String, String>.fromHandlers(handleData: (email, sink) {
    RegExp _regExp = RegExp(
        r"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?)*$");

    if (_regExp.hasMatch(email)) {
      sink.add(email);
    } else {

      sink.addError('Enter a valid email');
    }
  });

  final validatePassword = StreamTransformer<String, String>.fromHandlers(
      handleData: (password, sink) {
    if (password.length >= 5) {
      sink.add(password);
    } else {
      sink.addError('Password must be at least 5 characters');
    }
  });

  final validateName =
      StreamTransformer<String, String>.fromHandlers(handleData: (name, sink) {
    if (name != null && name.isNotEmpty) {
      sink.add(name);
    } else {
      sink.addError('Name cannot be empty');
    }
  });

  checkValidation(String name,String email,String password){
    bool flag = true;
    if(name==null || name.isEmpty){
      flag = false;
    }
    RegExp _regExp = RegExp(
        r"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?)*$");

    if (email==null || !_regExp.hasMatch(email)) {
      flag = false;
    }

    if(password==null || password.length<5){
      flag = false;
    }

    return flag;
  }

  checkValidationWithoutName(String email,String password){
    bool flag = true;
    RegExp _regExp = RegExp(
        r"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,253}[a-zA-Z0-9])?)*$");

    if (email==null || !_regExp.hasMatch(email)) {
      flag = false;
    }

    if(password==null || password.length<5){
      flag = false;
    }

    return flag;
  }
}
