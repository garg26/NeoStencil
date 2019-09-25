// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'SignUpModel.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SignUpModel _$SignUpModelFromJson(Map<String, dynamic> json) {
  return SignUpModel()
    ..isLoading = json['isLoading'] as bool
    ..name = json['name'] as String
    ..emailId = json['emailId'] as String
    ..password = json['password'] as String;
}

Map<String, dynamic> _$SignUpModelToJson(SignUpModel instance) =>
    <String, dynamic>{
      'isLoading': instance.isLoading,
      'name': instance.name,
      'emailId': instance.emailId,
      'password': instance.password
    };
