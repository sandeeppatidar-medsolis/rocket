CREATE TABLE public.branch (
    id bigserial NOT NULL,
    name character varying(255) NOT NULL,
    address character varying(255),
    country_id bigserial NOT NULL,
    state_id bigserial NOT NULL,
    city_id bigserial NOT NULL,
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone,
    last_modified_by character varying(255),
    CONSTRAINT branch_pkey PRIMARY KEY (id),
    FOREIGN KEY(created_by) REFERENCES public.users (username),
    FOREIGN KEY(last_modified_by) REFERENCES public.users (username),
    FOREIGN KEY(country_id) REFERENCES public.country (id),
    FOREIGN KEY(state_id) REFERENCES public.state (id),
    FOREIGN KEY(city_id) REFERENCES public.city (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.branch
  OWNER TO postgres;