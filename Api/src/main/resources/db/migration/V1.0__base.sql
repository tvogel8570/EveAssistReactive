-- public.eve_assist_user_id_seq definition
-- DROP SEQUENCE IF EXISTS public.eve_assist_user_id_seq;
CREATE SEQUENCE IF NOT EXISTS public.eve_assist_user_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 100
    CACHE 1
    NO CYCLE;

-- public.eve_pilot_id_seq definition
-- DROP SEQUENCE IF EXISTS public.eve_pilot_id_seq;
CREATE SEQUENCE IF NOT EXISTS public.eve_pilot_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

-- public.eve_assist_user definition
-- DROP TABLE public.eve_assist_user;
CREATE TABLE IF NOT EXISTS public.eve_assist_user
(
    id          int8         NOT NULL DEFAULT nextval('eve_assist_user_id_seq'),
    create_date timestamp    NOT NULL,
    email       varchar(200) NOT NULL,
    password    text         NOT NULL,
    user_unique varchar(30)  NOT NULL,
    screen_name varchar(100) NOT NULL,
    CONSTRAINT eve_assist_user_email_key UNIQUE (email),
    CONSTRAINT eve_assist_user_business_key UNIQUE (user_unique),
    CONSTRAINT eve_assist_user_pkey PRIMARY KEY (id)
);


-- public.eve_pilot definition
-- DROP TABLE public.eve_pilot;
CREATE TABLE IF NOT EXISTS public.eve_pilot
(
    id                 int8         NOT NULL DEFAULT nextval('eve_pilot_id_seq'),
    base_portrait_link varchar(100) NOT NULL,
    corporation_id     int8         NOT NULL,
    eve_owner_id       int8         NOT NULL,
    eve_pilot_id       int8         NOT NULL,
    pilot_name         varchar(25)  NOT NULL,
    refresh_token      text         NULL,
    security_status    float8       NOT NULL,
    fk_eau             int8         NOT NULL,
    CONSTRAINT eve_pilot_business_key UNIQUE (eve_owner_id, eve_pilot_id),
    CONSTRAINT eve_pilot_eve_assist_user_fk FOREIGN KEY (fk_eau) REFERENCES public.eve_assist_user (id),
    CONSTRAINT eve_pilot_pkey PRIMARY KEY (id)
);
