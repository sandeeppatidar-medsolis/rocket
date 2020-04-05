CREATE TABLE public.users (
    username character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_date timestamp without time zone,
    last_modified_by character varying(255),
    account_non_expired boolean NOT NULL,
    account_non_locked boolean NOT NULL,
    city character varying(255),
    credentials_non_expired boolean NOT NULL,
    email character varying(255),
    enabled boolean NOT NULL,
    location character varying(255),
    name character varying(255) NOT NULL,
    password character varying(255),
    phone character varying(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.users
  OWNER TO postgres;


CREATE TABLE public.roles (
    name character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    last_modified_date timestamp without time zone,
    last_modified_by character varying(255),
    display_name character varying(255),
    CONSTRAINT roles_pkey PRIMARY KEY (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.roles
  OWNER TO postgres;


CREATE TABLE public.users_roles
(
   user_id character varying(255) NOT NULL,
   role_id character varying(255) NOT NULL,
  CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id)
      REFERENCES public.users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id)
      REFERENCES public.roles (name) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.users_roles
  OWNER TO postgres;


INSERT INTO public.roles(name, created_by, display_name,created_date) VALUES ('ROLE_SUPER_ADMIN', 'SYSYEM', 'Super Administrator',now());

INSERT INTO public.users(username, account_non_expired, account_non_locked, created_by, credentials_non_expired, email, enabled, name, password, phone, created_date) VALUES ('superadmin', true, true,'SYSTEM', true, 'superadmin@rocket.com', true, 'Super Admin', '$2a$10$77DlCs2VLXhV1JJ/cZqpOe4EzghKEoAcW0h8SANEHRB7Qzx7HOCnW','1010101010',now());


INSERT INTO public.users_roles(user_id, role_id) VALUES ('superadmin', 'ROLE_SUPER_ADMIN');