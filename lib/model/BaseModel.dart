import 'package:json_annotation/json_annotation.dart';

part 'BaseModel.g.dart';

@JsonSerializable()

class BaseModel{

  bool _isLoading;

  set  isLoading(bool isLoading) => _isLoading = isLoading;

  bool get isLoading => _isLoading;
}