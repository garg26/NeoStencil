// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'login_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

LoginResponse _$LoginResponseFromJson(Map<String, dynamic> json) {
  return LoginResponse(
      json['loginSucces'] as bool,
      json['accessToken'] as String,
      json['userName'] as String,
      json['errorMessage'] as String)
    ..isLoading = json['isLoading'] as bool;
}

Map<String, dynamic> _$LoginResponseToJson(LoginResponse instance) =>
    <String, dynamic>{
      'isLoading': instance.isLoading,
      'loginSucces': instance.loginSucces,
      'accessToken': instance.accessToken,
      'userName': instance.userName,
      'errorMessage': instance.errorMessage
    };
