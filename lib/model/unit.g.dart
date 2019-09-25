// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'unit.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UnitModel _$UnitModelFromJson(Map<String, dynamic> json) {
  return UnitModel()
    ..unitId = json['unitId'] as int
    ..type = json['type'] as String
    ..typeId = json['typeId'] as int
    ..title = json['title'] as String
    ..description = json['description'] as String
    ..product = json['product'] as bool
    ..price = (json['price'] as num)?.toDouble()
    ..topic = json['topic'] as String
    ..position = json['position'] as int
    ..free = json['free'] as bool
    ..status = json['status'] as String;
}

Map<String, dynamic> _$UnitModelToJson(UnitModel instance) => <String, dynamic>{
      'unitId': instance.unitId,
      'type': instance.type,
      'typeId': instance.typeId,
      'title': instance.title,
      'description': instance.description,
      'product': instance.product,
      'price': instance.price,
      'topic': instance.topic,
      'position': instance.position,
      'free': instance.free,
      'status': instance.status
    };
