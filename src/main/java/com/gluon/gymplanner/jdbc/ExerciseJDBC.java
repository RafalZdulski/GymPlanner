package com.gluon.gymplanner.jdbc;

import com.gluon.gymplanner.dtos.ExerciseDetails;


import java.util.ArrayList;
import java.util.List;


/** @Deprecated to delete*/
//TODO remove this class
public class ExerciseJDBC {

    public List<ExerciseDetails> getAllExercises(){
        return List.of(
                new ExerciseDetails("Close-grip push-up", "https://cdn-0.weighttraining.guide/wp-content/uploads/2016/11/close-grip-push-up-resized-768x512.png",
                    new String[]{"arms","chest"}, new String[]{"Triceps Brachii"}, "compound", "push"),
                new ExerciseDetails("Incline dumbbell curl", "com/gluon/gymplanner/exercises/images/Incline-Dumbbell-Curl.webp",
                        new String[]{"arms"}, new String[]{" Biceps Brachii"}, "Isolation", "pull"),
                new ExerciseDetails("Seated dumbbell overhead triceps extension", "https://cdn-0.weighttraining.guide/wp-content/uploads/2017/08/seated-dumbbell-overhead-triceps-extension-resized.png",
                        new String[]{"arms"}, new String[]{"Triceps Brachii"}, "isolation", "push"),
                new ExerciseDetails("One-arm reverse dumbbell fly", "https://cdn-0.weighttraining.guide/wp-content/uploads/2018/12/One-arm-dumbbell-bent-over-lateral-raise-resized-1024x683.png",
                        new String[]{"shoulders"}, new String[]{"Posterior Deltoid"}, "isolation", "pull"),
                new ExerciseDetails("Kettlebell deadlift", "https://cdn-0.weighttraining.guide/wp-content/uploads/2022/01/Kettlebell-deadlift-1-1024x683.png",
                        new String[]{"legs and glutes"}, new String[]{"Gluteus Maximus"}, "compound", "pull"),
                new ExerciseDetails("Barbell good morning", "https://cdn-0.weighttraining.guide/wp-content/uploads/2021/03/Barbell-good-morning.png",
                        new String[]{"back","legs and glutes"}, new String[]{"Hamstrings"}, "isolation", "pull"),
                new ExerciseDetails("Dumbbell goblet split squat", "https://cdn-0.weighttraining.guide/wp-content/uploads/2022/01/Dumbbell-goblet-split-squat.png",
                        new String[]{"legs and glutes"}, new String[]{"Quadriceps"}, "compound", "push"),
                new ExerciseDetails("Dumbbell front squat", "https://cdn-0.weighttraining.guide/wp-content/uploads/2022/01/Dumbbell-front-squat.png",
                        new String[]{"legs and glutes"}, new String[]{"Quadriceps"}, "compound", "push"),
                new ExerciseDetails("Seated dumbbell one-arm shoulder press", "https://cdn-0.weighttraining.guide/wp-content/uploads/2021/09/Seated-dumbbell-one-arm-shoulder-press.png",
                        new String[]{"shoulders"}, new String[]{"Anterior Deltoid"}, "compound", "push"),
                new ExerciseDetails("Barbell sumo deadlift", "https://cdn-0.weighttraining.guide/wp-content/uploads/2017/02/Barbell-sumo-deadlift-1.png",
                        new String[]{"back","legs and glutes"}, new String[]{"Gluteus Maximus", "Erector Spinae"}, "compound", "pull"),
                new ExerciseDetails("Lying reverse dumbbell fly", "https://cdn-0.weighttraining.guide/wp-content/uploads/2018/05/Lying-dumbbell-rear-lateral-raise-resized.png",
                        new String[]{"back","shoulders"}, new String[]{"Posterior Deltoid"}, "isolation", "pull"),
                new ExerciseDetails("Bicycle crunch", "https://cdn-0.weighttraining.guide/wp-content/uploads/2016/11/bicycle-crunch-resized-1.png",
                        new String[]{"core","legs and glutes"}, new String[]{"Rectus Abdominis"}, "compound", "pull"),
                new ExerciseDetails("Weighted Russian twist", "https://cdn-0.weighttraining.guide/wp-content/uploads/2016/11/weighted-russian-twist-resized.png",
                        new String[]{"core"}, new String[]{"Internal Obliques","External Obliques"}, "isolation", "pull"),
                new ExerciseDetails("Stability ball front plank", "https://cdn-0.weighttraining.guide/wp-content/uploads/2022/02/Stability-ball-front-plank.png",
                        new String[]{"core"}, new String[]{"Rectus Abdominis"}, "isolation", "push")
//                new ExerciseDetails("", "",
//                        new String[]{""}, new String[]{""}, "", "")
        );
    }

    public List<String> getAllBodyParts(){
        return List.of(
                "arms",
                "shoulders",
                "chest",
                "core",
                "back",
                "legs and glutes"
        );
    }

    public List<ExerciseDetails> getFiltered(String name, String force, String mechanics, List<String> bodyParts){
        List<ExerciseDetails> allExercises = getAllExercises();
        List<ExerciseDetails> ret = new ArrayList<>();

        //TODO REFACTOR: filtering by name should work similar to regex
        for (var exercise : allExercises){
            if (!name.isEmpty() && !exercise.getName().toLowerCase().contains(name.toLowerCase()))
                continue;
            if (force != null)
                if (!exercise.getForce().equals(force))
                    continue;
            if (mechanics != null)
                if (!exercise.getMechanics().equals(mechanics))
                    continue;
            if (!bodyParts.isEmpty()){
                //TODO QUESTION: should bodyParts filters work in OR or AND - now its OR
                boolean match = false;
                for (var bp : exercise.getBodyParts())
                    if (bodyParts.contains(bp)) {
                        match = true;
                        break;
                    }
                if (!match){
                    continue;
                }
            }
            ret.add(exercise);
        }

        return ret;
    }
}
