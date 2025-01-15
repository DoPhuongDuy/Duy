 CREATE TABLE "role" (
   "id" serial PRIMARY KEY,
   "role_name" varchar(50) UNIQUE NOT NULL,
   "description" varchar(256),
   "created_at" timestamp,
   "updated_at" timestamp
 );

CREATE TABLE "package_plan" (
  "id" serial PRIMARY KEY,
  "plan_name" varchar(50) UNIQUE NOT NULL,
  "description" varchar(256),
  "max_events" int NOT NULL,
  "max_projects" int NOT NULL,
  "max_judges" int NOT NULL,
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "user_package_mapping" (
  "user_id" int,
  "package_plan_id" int,
  "created_at" timestamp,
  PRIMARY KEY ("user_id", "package_plan_id")
);

CREATE TABLE "user_role_mapping" (
  "user_id" int,
  "role_id" int,
  "created_at" timestamp,
  PRIMARY KEY ("user_id", "role_id")
);

CREATE TABLE "users" (
  "id" serial PRIMARY KEY,
  "account" varchar(64) UNIQUE NOT NULL,
  "pwd" varchar(64) NOT NULL,
  "first_name" varchar(64) NOT NULL,
  "last_name" varchar(64) NOT NULL,
  "email" varchar(64) UNIQUE NOT NULL,
  "phone" varchar(64) UNIQUE NOT NULL,
  "round_enable" boolean DEFAULT false,
  "description" varchar(256),
  "created_at" timestamp,
  "updated_at" timestamp
);

CREATE TABLE "event" (
  "id" serial PRIMARY KEY,
  "event_name" varchar(50) NOT NULL,
  "start_date" date,
  "end_date" date,
  "description" varchar(256),
  "organizer_id" int,
  "school_id" int,
  "created_at" timestamp,
  "modified_at" timestamp,
  "modified_by" varchar(64),
  "created_by" varchar(64)
);

CREATE TABLE "category" (
  "id" serial PRIMARY KEY,
  "name" varchar(50) UNIQUE NOT NULL,
  "description" varchar(256),
  "event_id" int,
  "created_at" timestamp,
  "modified_at" timestamp,
  "modified_by" varchar(64),
  "created_by" varchar(64)
);

CREATE TABLE "criteria_pack" (
  "id" serial PRIMARY KEY,
  "pack_name" varchar(50) NOT NULL,
  "description" varchar(256),
  "round_number" int NOT NULL,
  "event_id" int,
  "created_at" timestamp,
  "modified_at" timestamp,
  "modified_by" varchar(64),
  "created_by" varchar(64)
);

CREATE TABLE "criteria" (
  "id" serial PRIMARY KEY,
  "criteria_name" varchar(50) NOT NULL,
  "description" varchar(100),
  "weight" int NOT NULL,
  "category_id" int,
  "created_at" timestamp,
  "modified_at" timestamp,
  "modified_by" varchar(64),
  "created_by" varchar(64)
);

CREATE TABLE "criteria_pack_mapping" (
  "criteria_id" int,
  "pack_id" int,
  PRIMARY KEY ("criteria_id", "pack_id")
);

CREATE TABLE "project" (
  "id" serial PRIMARY KEY,
  "group_name" varchar(50) NOT NULL,
  "title" varchar(20) NOT NULL,
  "client" varchar(20),
  "category_id" int,
  "average_mark_v1" double precision,
  "average_mark_v2" double precision,
  "description" varchar(512),
  "created_at" timestamp,
  "modified_at" timestamp,
  "modified_by" varchar(64),
  "created_by" varchar(64),
  "event_id" int
);

CREATE TABLE "judge_project" (
  "user_id" int,
  "project_id" int,
  PRIMARY KEY ("user_id", "project_id")
);

CREATE TABLE "marking_round" (
  "id" serial PRIMARY KEY,
  "round" int NOT NULL,
  "criteria_id" int,
  "user_id" int,
  "mark" int NOT NULL,
  "is_marked" boolean NOT NULL DEFAULT false,
  "project_id" int,
  "description" varchar(256),
  "created_at" timestamp,
  "modified_at" timestamp,
  "modified_by" varchar(64),
  "created_by" varchar(64)
);

COMMENT ON COLUMN "role"."role_name" IS 'Name of the role (e.g., admin, organizer, judge, etc.)';

COMMENT ON COLUMN "role"."description" IS 'Description of the role';

COMMENT ON COLUMN "package_plan"."plan_name" IS 'Name of the package or plan';

COMMENT ON COLUMN "package_plan"."description" IS 'Description of the plan';

COMMENT ON COLUMN "package_plan"."max_events" IS 'Maximum number of events the organizer can create';

COMMENT ON COLUMN "package_plan"."max_projects" IS 'Maximum number of projects the organizer can create per event';

COMMENT ON COLUMN "package_plan"."max_judges" IS 'Maximum number of judges the organizer can assign per event';

COMMENT ON COLUMN "users"."round_enable" IS 'Applicable for judges';

COMMENT ON COLUMN "event"."organizer_id" IS 'References users.id where role = organizer';

COMMENT ON COLUMN "event"."school_id" IS 'References school.id';

COMMENT ON COLUMN "category"."event_id" IS 'References event.id';

COMMENT ON COLUMN "criteria_pack"."round_number" IS 'Associated round number';

COMMENT ON COLUMN "criteria_pack"."event_id" IS 'References event.id';

COMMENT ON COLUMN "criteria"."weight" IS 'Weight of the criteria in scoring';

COMMENT ON COLUMN "criteria"."category_id" IS 'References category.id';

COMMENT ON COLUMN "project"."category_id" IS 'References category.id';

COMMENT ON COLUMN "project"."event_id" IS 'References event.id';

COMMENT ON COLUMN "marking_round"."criteria_id" IS 'References criteria.id';

COMMENT ON COLUMN "marking_round"."user_id" IS 'References users.id where role = judge';

COMMENT ON COLUMN "marking_round"."project_id" IS 'References project.id';

ALTER TABLE "user_package_mapping" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_package_mapping" ADD FOREIGN KEY ("package_plan_id") REFERENCES "package_plan" ("id");

ALTER TABLE "user_role_mapping" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_role_mapping" ADD FOREIGN KEY ("role_id") REFERENCES "role" ("id");

ALTER TABLE "event" ADD FOREIGN KEY ("organizer_id") REFERENCES "users" ("id");

ALTER TABLE "category" ADD FOREIGN KEY ("event_id") REFERENCES "event" ("id");

ALTER TABLE "criteria_pack" ADD FOREIGN KEY ("event_id") REFERENCES "event" ("id");

ALTER TABLE "criteria" ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");

ALTER TABLE "criteria_pack_mapping" ADD FOREIGN KEY ("criteria_id") REFERENCES "criteria" ("id");

ALTER TABLE "criteria_pack_mapping" ADD FOREIGN KEY ("pack_id") REFERENCES "criteria_pack" ("id");

ALTER TABLE "project" ADD FOREIGN KEY ("event_id") REFERENCES "event" ("id");

ALTER TABLE "project" ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");

ALTER TABLE "judge_project" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "judge_project" ADD FOREIGN KEY ("project_id") REFERENCES "project" ("id");

ALTER TABLE "marking_round" ADD FOREIGN KEY ("criteria_id") REFERENCES "criteria" ("id");

ALTER TABLE "marking_round" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "marking_round" ADD FOREIGN KEY ("project_id") REFERENCES "project" ("id");

ALTER TABLE user_package_mapping ADD COLUMN number_event INT;

ALTER TABLE "category" DROP CONSTRAINT "category_event_id_fkey";
ALTER TABLE "category" DROP COLUMN "event_id";

ALTER TABLE "project" DROP CONSTRAINT "project_category_id_fkey";
ALTER TABLE "project" DROP COLUMN "category_id";

ALTER TABLE "criteria_pack" DROP CONSTRAINT "criteria_pack_event_id_fkey";
ALTER TABLE "criteria_pack" DROP COLUMN "event_id";

ALTER TABLE "marking_round" DROP CONSTRAINT "marking_round_criteria_id_fkey";
ALTER TABLE "marking_round" DROP COLUMN "criteria_id";

ALTER TABLE "marking_round" ADD COLUMN "pack_id" INT;

ALTER TABLE "marking_round"
ADD CONSTRAINT "fk_marking_round_criteria_pack"
FOREIGN KEY ("pack_id") REFERENCES "criteria_pack" ("id");

ALTER TABLE "criteria_pack" ADD COLUMN "event_id" INT;
ALTER TABLE "criteria_pack"
ADD CONSTRAINT fk_event_id
FOREIGN KEY ("event_id") REFERENCES "event" ("id")
ON DELETE CASCADE;

ALTER TABLE "marking_round"
DROP CONSTRAINT fk_marking_round_criteria_pack;
ALTER TABLE "marking_round"
DROP COLUMN "pack_id";

CREATE TABLE "marking_round_criteria_pack_mapping" (
  "marking_round_id" INT,
  "criteria_pack_id" INT,
  PRIMARY KEY ("marking_round_id", "criteria_pack_id"),
  FOREIGN KEY ("marking_round_id") REFERENCES "marking_round"("id")
    ON DELETE CASCADE,
  FOREIGN KEY ("criteria_pack_id") REFERENCES "criteria_pack"("id")
    ON DELETE CASCADE
);


