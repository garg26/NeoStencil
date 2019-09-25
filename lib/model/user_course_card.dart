
class UserCourseCard extends Object{

  String courseTitle;
  String instructorName;
  String instituteName;
  int studentsEnrolled;
  String courseOldSlug;
  String courseImage;
  int courseBatchId;
  String status;
  String startDate;
  int remainingValidity;


  UserCourseCard(this.courseTitle, this.instructorName, this.instituteName,
      this.studentsEnrolled, this.courseOldSlug, this.courseImage,
      this.courseBatchId, this.status, this.startDate, this.remainingValidity);

  UserCourseCard.fromJson(dynamic json)
      :courseTitle = json['courseTitle'] as String,
      instructorName = json['instructorName'] as String,
      instituteName = json['instituteName'] as String,
      studentsEnrolled = json['studentsEnrolled'] as int,
      courseOldSlug = json['courseOldSlug'] as String,
      courseImage = json['courseImage'] as String,
      courseBatchId = json['courseBatchId'] as int,
      status = json['status'] as String,
      startDate = json['startDate'] as String,
      remainingValidity = json['remainingValidity'] as int;



  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
          other is UserCourseCard &&
              runtimeType == other.runtimeType &&
              courseBatchId == other.courseBatchId;

  @override
  int get hashCode => courseBatchId.hashCode;


}