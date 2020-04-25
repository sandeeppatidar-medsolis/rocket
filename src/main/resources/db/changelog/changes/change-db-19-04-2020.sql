
ALTER TABLE users DROP COLUMN city, DROP COLUMN email, DROP COLUMN location, DROP COLUMN name, DROP COLUMN phone;

create type gender_enum as enum('MALE', 'FEMALE', 'OTHER');
create type employee_type_enum as enum('EMPLOYEE', 'AGENT');

CREATE TABLE public.employee (
    id bigserial NOT NULL,
    firstname character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    gender gender_enum NOT NULL,
    avatar character varying(255),
    dob timestamp without time zone NOT NULL,
    doj timestamp without time zone NOT NULL,
    mobile character varying(255) NOT NULL,
    account_number character varying(255),
    ifsc_code character varying(255),
    salary decimal,
    employee_type employee_type_enum NOT NULL,
    email character varying(255),
    address character varying(255),
    user_id character varying(255) NOT NULL,
    branch_id bigserial NOT NULL,
    country_id bigserial,
    state_id bigserial,
    city_id bigserial,
    created_by character varying(255) NOT NULL,
    created_date timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone,
    last_modified_by character varying(255),
    CONSTRAINT employee_pkey PRIMARY KEY (id),
    FOREIGN KEY(created_by) REFERENCES public.users (username),
    FOREIGN KEY(last_modified_by) REFERENCES public.users (username),
    FOREIGN KEY(country_id) REFERENCES public.country (id),
    FOREIGN KEY(state_id) REFERENCES public.state (id),
    FOREIGN KEY(city_id) REFERENCES public.city (id),
    FOREIGN KEY(branch_id) REFERENCES public.branch (id),
    FOREIGN KEY(user_id) REFERENCES public.users (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.employee
  OWNER TO postgres;
