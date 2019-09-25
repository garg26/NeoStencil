import 'dart:collection';

import 'package:NeoStencil/model/user_batch_unit_list.dart';
import 'package:json_annotation/json_annotation.dart';

part 'user_batch_curriculum_list.g.dart';

@JsonSerializable()
class UserBatchCurriculumList{

  LinkedHashMap<String,List<UserBatchUnitModel>> _response;

  UserBatchCurriculumList();


  LinkedHashMap<String, List<UserBatchUnitModel>> get response => _response;

  set response(LinkedHashMap<String, List<UserBatchUnitModel>> value) {
    _response = value;
  }

  factory UserBatchCurriculumList.fromJson(Map<String, dynamic> json) => _$UserBatchCurriculumListFromJson(json);


  Map<String, dynamic> toJson() => _$UserBatchCurriculumListToJson(this);

}