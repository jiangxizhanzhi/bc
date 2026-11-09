CREATE TABLE "cr_demo_data_run" (
    "business_date" DATE NOT NULL PRIMARY KEY,
    "generated_count" INT NOT NULL,
    "create_time" TIMESTAMP NOT NULL
);

CREATE TABLE "cr_demo_data_room" (
    "room_id" VARCHAR(64) NOT NULL PRIMARY KEY,
    "source_room_id" VARCHAR(64) NOT NULL,
    "business_date" DATE NOT NULL,
    "topo_digest" CLOB NOT NULL
);
CREATE INDEX "idx_demo_data_business_date" ON "cr_demo_data_room" ("business_date");
