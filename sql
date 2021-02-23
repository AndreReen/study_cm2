
CREATE TABLE public.cmdata
(
    cm_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    cm_type character varying COLLATE pg_catalog."default" NOT NULL,
    cm_qnty integer,
    CONSTRAINT cmdata_pkey PRIMARY KEY (cm_id),
    CONSTRAINT quantity CHECK (cm_qnty >= 0 AND cm_qnty <= 10)
);

INSERT INTO cmdata (cm_type, cm_qnty)
VALUES ( 'cappucino', 10),
('americano', 10), 
('espresso', 10),
('latte', 10);
