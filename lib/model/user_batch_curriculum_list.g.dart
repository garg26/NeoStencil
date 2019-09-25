// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_batch_curriculum_list.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserBatchCurriculumList _$UserBatchCurriculumListFromJson(
    Map<String, dynamic> json) {
  return UserBatchCurriculumList()
    ..response = (json['response'] as Map<String, dynamic>)?.map((k, e) =>
        MapEntry(
            k,
            (e as List)
                ?.map((e) => e == null
                    ? null
                    : UserBatchUnitModel.fromJson(e as Map<String, dynamic>))
                ?.toList()));
}

Map<String, dynamic> _$UserBatchCurriculumListToJson(
        UserBatchCurriculumList instance) =>
    <String, dynamic>{'response': instance.response};
