package com.neostencilmobileapp.methods;

import com.neostencilmobileapp.model.MobileNote;
import com.neostencilmobileapp.model.PlayLectureOnMobile;
import com.neostencilmobileapp.model.UnitModel;

public interface ShowUnit {

   void playLecture(PlayLectureOnMobile lectureOnMobile,UnitModel unit,double duration);

   void showAssignment(UnitModel assignment);

   void showNotes(MobileNote notes);
}
