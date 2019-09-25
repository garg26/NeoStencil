

class UnitType {

  final _value;
  const UnitType._internal(this._value);
  toString() => '$_value';


  static const Lectures = const UnitType._internal('LECTURE');
  static const Assignment = const UnitType._internal('ASSIGNMENT');
  static const Notes = const UnitType._internal('NOTES');
  static const Quiz = const UnitType._internal('QUIZ');
}