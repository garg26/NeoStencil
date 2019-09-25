import 'package:NeoStencil/blocs/bloc_base.dart';
import 'package:NeoStencil/blocs/form_field_validators.dart';
import 'package:rxdart/rxdart.dart';
import 'package:NeoStencil/model/SignUpModel.dart';
import 'dart:async';

class LoginEmailBloc extends Object with Validators implements BlocBase{
  final _email = BehaviorSubject<String>();
  final _password = BehaviorSubject<String>();
  final _isLoading = BehaviorSubject<bool>(seedValue: false);


  ///add data to the stream
  Function(String) get changePassword => _password.sink.add;

  Function(String) get changeEmail => _email.sink.add;

  void get hideLoader => _isLoading.sink.add(false);

  void get showLoader => _isLoading.sink.add(true);


  ///get stream data
  Stream<String> get getEmail => _email.stream.transform(validateEmail);


  Stream<String> get getPassword =>
      _password.stream.transform(validatePassword);

  Stream<bool> get isLoading => _isLoading.stream;

  SignUpModel onSubmit() {
    final emailId = _email.value;
    final password = _password.value;

    if (checkValidationWithoutName(emailId, password)) {
      _isLoading.sink.add(true);
      SignUpModel signUpModel =
      new SignUpModel.name(null, emailId, password);

      return signUpModel;
    }

    return null;
  }

  dispose() {
    _email.close();
    _password.close();
    _isLoading.close();
  }
}