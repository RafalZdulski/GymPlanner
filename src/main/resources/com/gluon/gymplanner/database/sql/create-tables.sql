CREATE TABLE "EXERCISE_DETAILS"
(
    "id"        varchar(45) NOT NULL ,
    "name"      varchar(255) NOT NULL ,
    "pic_url"   varchar(255) NOT NULL ,
    "mechanics" varchar(45) NOT NULL ,
    "force"     varchar(45) NOT NULL ,
    "primary_body_part"     varchar(45) NOT NULL ,
    "secondary_body_part"     varchar(45),
    "equipment_1" varchar(45),
    "equipment_2" varchar(45),

    PRIMARY KEY ("id")
);

CREATE TABLE "EXECUTION"
(
    "exercise_id" varchar(45) NOT NULL ,
    "position"    numeric NOT NULL ,
    "text"        varchar(255) NOT NULL ,

    PRIMARY KEY ("exercise_id", "position"),
    CONSTRAINT "FK_63" FOREIGN KEY ("exercise_id") REFERENCES "EXERCISE_DETAILS" ("id")
);

CREATE TABLE "TARGET_MUSCLES"
(
    "id"          INT NOT NULL AUTO_INCREMENT ,
    "exercise_id" varchar(45) NOT NULL ,
    "muscle"      varchar(45) NOT NULL ,

    PRIMARY KEY ("id", "exercise_id"),
    CONSTRAINT "FK_28" FOREIGN KEY ("exercise_id") REFERENCES "EXERCISE_DETAILS" ("id")
);

CREATE TABLE "TIPS"
(
    "exercise_id" varchar(45) NOT NULL ,
    "id"          numeric NOT NULL ,
    "tip"         varchar(511) NOT NULL ,

    PRIMARY KEY ("exercise_id", "id"),
    CONSTRAINT "FK_69" FOREIGN KEY ("exercise_id") REFERENCES "EXERCISE_DETAILS" ("id")
);

CREATE TABLE "USER"
(
    "login"    varchar(45) NOT NULL ,
    "password" varchar(45) NOT NULL ,
    "email"    varchar(45) NOT NULL ,

    PRIMARY KEY ("login")
);

CREATE TABLE "PLAN"
(
    "id"   varchar(45) NOT NULL ,
    "user" varchar(45) NOT NULL ,

    PRIMARY KEY ("id"),
    CONSTRAINT "FK_136" FOREIGN KEY ("user") REFERENCES "USER" ("login")
);

CREATE TABLE "WORKOUT"
(
    "workout_id" varchar(45) NOT NULL ,
    "date"       datetime NOT NULL ,
    "plan_id"    varchar(45) NOT NULL ,

    PRIMARY KEY ("workout_id"),
    CONSTRAINT "FK_126" FOREIGN KEY ("plan_id") REFERENCES "PLAN" ("id")
);

CREATE TABLE "WORKOUT_DETAILS"
(
    "exercise_id"  varchar(45) NOT NULL ,
    "workout_id"   varchar(45) NOT NULL ,
    "exercise_pos" numeric NOT NULL ,
    "series_pos"   numeric NOT NULL ,
    "weight"       numeric NOT NULL ,
    "reps_planned" numeric NOT NULL ,
    "reps_done"    numeric NOT NULL ,

    PRIMARY KEY ("exercise_id", "workout_id", "exercise_pos", "series_pos"),
    CONSTRAINT "FK_105" FOREIGN KEY ("exercise_id") REFERENCES "EXERCISE_DETAILS" ("id"),
    CONSTRAINT "FK_109" FOREIGN KEY ("workout_id") REFERENCES "WORKOUT" ("workout_id")
);

