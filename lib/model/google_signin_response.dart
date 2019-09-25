import 'package:NeoStencil/model/BaseModel.dart';
import 'package:json_annotation/json_annotation.dart';

part 'google_signin_response.g.dart';


@JsonSerializable()
class GoogleSignInResponse {

  String name;
  String email;
  String picture;

  GoogleSignInResponse(this.name, this.email, this.picture);

  factory GoogleSignInResponse.fromJson(Map<String, dynamic> json) => _$GoogleSignInResponseFromJson(json);


  Map<String, dynamic> toJson() => _$GoogleSignInResponseToJson(this);


}