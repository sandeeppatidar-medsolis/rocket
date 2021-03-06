CREATE TABLE public.country (
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone,
    last_modified_by character varying(255),
    CONSTRAINT country_pkey PRIMARY KEY (id),
    FOREIGN KEY(created_by) REFERENCES public.users (username),
    FOREIGN KEY(last_modified_by) REFERENCES public.users (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.country
  OWNER TO postgres;


CREATE TABLE public.state (
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    country_id bigserial NOT NULL,
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone,
    last_modified_by character varying(255),
    CONSTRAINT state_pkey PRIMARY KEY (id),
    FOREIGN KEY(country_id) REFERENCES public.country (id),
    FOREIGN KEY(created_by) REFERENCES public.users (username),
    FOREIGN KEY(last_modified_by) REFERENCES public.users (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.state
  OWNER TO postgres;


CREATE TABLE public.city (
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    state_id bigserial NOT NULL,
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone,
    last_modified_by character varying(255),
    CONSTRAINT city_pkey PRIMARY KEY (id),
    FOREIGN KEY(state_id) REFERENCES public.state (id),
    FOREIGN KEY(created_by) REFERENCES public.users (username),
    FOREIGN KEY(last_modified_by) REFERENCES public.users (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.city
  OWNER TO postgres;
