
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



CREATE TABLE public.cmcustomer
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "unique" UNIQUE (name)
)

CREATE TABLE public.sale
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    customer character varying COLLATE pg_catalog."default",
    beverage character varying COLLATE pg_catalog."default",
    date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT sale_pkey PRIMARY KEY (id),
    CONSTRAINT fkey_beverage FOREIGN KEY (beverage)
        REFERENCES public.cmdata (cm_type) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fkey_customer FOREIGN KEY (customer)
        REFERENCES public.cmcustomer (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)
