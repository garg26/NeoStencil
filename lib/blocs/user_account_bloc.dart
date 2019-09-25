import 'package:NeoStencil/blocs/bloc_base.dart';
import 'package:NeoStencil/blocs/form_field_validators.dart';
import 'package:rxdart/rxdart.dart';
import 'package:NeoStencil/model/SignUpModel.dart';
import 'dart:async';

class UserAccountBloc extends Object with Validators implements BlocBase {
  final _fullName = BehaviorSubject<String>();
  final _email = BehaviorSubject<String>();
  final _password = BehaviorSubject<String>();
  final _isLoading = BehaviorSubject<bool>(seedValue: false);
  final _isGoogleLogin = BehaviorSubject<bool>(seedValue: false);


  ///add data to the stream
  Function(String) get changeName => _fullName.sink.add;

  Function(String) get changePassword => _password.sink.add;

  Function(String) get changeEmail => _email.sink.add;

  void get hideLoader => _isLoading.sink.add(false);

  void get showLoader => _isLoading.sink.add(true);

  void get hideGoogleLoader => _isGoogleLogin.sink.add(false);

  void get showFacebookLoader => _isGoogleLogin.sink.add(true);


  ///get stream data
  Stream<String> get getEmail => _email.stream.transform(validateEmail);

  Stream<String> get getName => _fullName.stream.transform(validateName);

  Stream<String> get getPassword =>
      _password.stream.transform(validatePassword);

  Stream<bool> get isLoading => _isLoading.stream;

  Stream<bool> get isGoogleLogin => _isGoogleLogin.stream;

  SignUpModel onSubmit() {
    final fullName = _fullName.value;
    final emailId = _email.value;
    final password = _password.value;

    if (checkValidation(fullName, emailId, password)) {
      _isLoading.sink.add(true);
      SignUpModel signUpModel =
       new SignUpModel.name(fullName, emailId, password);

      return signUpModel;
    }

    return null;
  }

  dispose() {
    _fullName.close();
    _email.close();
    _password.close();
    _isLoading.close();
    _isGoogleLogin.close();
  }

}



