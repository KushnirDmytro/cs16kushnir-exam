package domain;

import json.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class Student extends BasicStudent {

    private ArrayList<Tuple<String, Integer>> exams;

    public Student(String name, String surname, Integer year, Tuple<String, Integer>... exams) {
        super(name, surname, year);
        this.exams = new ArrayList<>(exams.length);
        Collections.addAll(this.exams, exams);
    }

    private JsonObject[] examsArray() {
        JsonObject[] resArray = new JsonObject[exams.size()];
        int index = 0;
        for (Tuple<String, Integer> exam : exams) {
            resArray[index++] = new JsonObject(
                    new JsonPair("course", new JsonString(exam.key)),
                    new JsonPair("mark", new JsonNumber(exam.value)),
                    new JsonPair("passed", new JsonBoolean(exam.value >= 3))
            );
        }
        return resArray;
    }

    @Override
    public JsonObject toJsonObject() {
        Json jYear = new JsonNumber(this.year);
        Json jName = new JsonString(this.name);
        Json jSurname = new JsonString(this.surname);
        ;
        Json jExams = new JsonArray(this.examsArray());

        JsonPair name = new JsonPair("name", jName);
        JsonPair surname = new JsonPair("surname", jSurname);
        JsonPair year = new JsonPair("year", jYear);
        JsonPair exams = new JsonPair("exams", jExams);
        JsonObject jsonObj = new JsonObject(name, surname, year, exams);

        return jsonObj;
    }
}