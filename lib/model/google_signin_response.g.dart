// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'google_signin_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GoogleSignInResponse _$GoogleSignInResponseFromJson(Map<String, dynamic> json) {
  return GoogleSignInResponse(json['name'] as String, json['email'] as String,
      json['picture'] as String);
}

Map<String, dynamic> _$GoogleSignInResponseToJson(
        GoogleSignInResponse instance) =>
    <String, dynamic>{
      'name': instance.name,
      'email': instance.email,
      'picture': instance.picture
    };
