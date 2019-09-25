// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_batch_unit_list.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserBatchUnitModel _$UserBatchUnitModelFromJson(Map<String, dynamic> json) {
  return UserBatchUnitModel()
    ..unit = json['unit'] == null
        ? null
        : UnitModel.fromJson(json['unit'] as Map<String, dynamic>)
    ..resumeFrom = (json['resumeFrom'] as num)?.toDouble()
    ..noOfClicks = json['noOfClicks'] as int
    ..watchStatus = json['watchStatus'] as String
    ..active = json['active'] as bool
    ..linkageId = json['linkageId'] as int
    ..updatedAt = json['updatedAt'] as int
    ..createdAt = json['createdAt'] as int;
}

Map<String, dynamic> _$UserBatchUnitModelToJson(UserBatchUnitModel instance) =>
    <String, dynamic>{
      'unit': instance.unit,
      'resumeFrom': instance.resumeFrom,
      'noOfClicks': instance.noOfClicks,
      'watchStatus': instance.watchStatus,
      'active': instance.active,
      'linkageId': instance.linkageId,
      'updatedAt': instance.updatedAt,
      'createdAt': instance.createdAt
    };
