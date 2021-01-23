--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.5
-- Dumped by pg_dump version 9.5.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: appointments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE appointments (
    id bigint NOT NULL,
    end_time timestamp without time zone,
    start_time timestamp without time zone,
    group_id bigint
);


ALTER TABLE appointments OWNER TO postgres;

--
-- Name: appointments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE appointments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointments_id_seq OWNER TO postgres;

--
-- Name: appointments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE appointments_id_seq OWNED BY appointments.id;


--
-- Name: faculties; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE faculties (
    id bigint NOT NULL,
    abbreviation character varying(255),
    address character varying(255),
    name character varying(255),
    start_registration timestamp without time zone
);


ALTER TABLE faculties OWNER TO postgres;

--
-- Name: faculties_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE faculties_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE faculties_id_seq OWNER TO postgres;

--
-- Name: faculties_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE faculties_id_seq OWNED BY faculties.id;


--
-- Name: fields_of_study; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE fields_of_study (
    id bigint NOT NULL,
    mode integer,
    name character varying(255),
    type integer,
    faculty_id bigint
);


ALTER TABLE fields_of_study OWNER TO postgres;

--
-- Name: fields_of_study_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fields_of_study_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE fields_of_study_id_seq OWNER TO postgres;

--
-- Name: fields_of_study_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE fields_of_study_id_seq OWNED BY fields_of_study.id;


--
-- Name: groups_in_fields_of_study; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE groups_in_fields_of_study (
    subject_groups_id bigint NOT NULL,
    fields_of_study_id bigint NOT NULL
);


ALTER TABLE groups_in_fields_of_study OWNER TO postgres;

--
-- Name: news; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE news (
    id bigint NOT NULL,
    description character varying(8000),
    time_of_publication timestamp without time zone,
    title character varying(255),
    author_id bigint
);


ALTER TABLE news OWNER TO postgres;

--
-- Name: news_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE news_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE news_id_seq OWNER TO postgres;

--
-- Name: news_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE news_id_seq OWNED BY news.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE roles (
    id bigint NOT NULL,
    name character varying(10)
);


ALTER TABLE roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE roles_id_seq OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE roles_id_seq OWNED BY roles.id;


--
-- Name: students; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE students (
    index_number bigint,
    semester integer,
    user_id bigint NOT NULL,
    field_of_study_id bigint
);


ALTER TABLE students OWNER TO postgres;

--
-- Name: students_in_groups; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE students_in_groups (
    subject_groups_id bigint NOT NULL,
    students_id bigint NOT NULL
);


ALTER TABLE students_in_groups OWNER TO postgres;

--
-- Name: subject_groups; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE subject_groups (
    id bigint NOT NULL,
    course_time time without time zone,
    number_of_places integer,
    place character varying(255),
    type integer,
    subject_id bigint,
    teacher_user_id bigint
);


ALTER TABLE subject_groups OWNER TO postgres;

--
-- Name: subject_groups_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE subject_groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE subject_groups_id_seq OWNER TO postgres;

--
-- Name: subject_groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE subject_groups_id_seq OWNED BY subject_groups.id;


--
-- Name: subjects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE subjects (
    id bigint NOT NULL,
    ects integer,
    course_type integer,
    description character varying(8000),
    name character varying(255),
    number_of_hours bigint,
    language character varying(255)
);


ALTER TABLE subjects OWNER TO postgres;

--
-- Name: subjects_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE subjects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE subjects_id_seq OWNER TO postgres;

--
-- Name: subjects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE subjects_id_seq OWNED BY subjects.id;


--
-- Name: teachers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE teachers (
    consultations character varying(255),
    room character varying(255),
    user_id bigint NOT NULL,
    faculty_id bigint
);


ALTER TABLE teachers OWNER TO postgres;

--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE user_roles OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    id bigint NOT NULL,
    email character varying(255),
    full_name character varying(255),
    is_active boolean,
    password character varying(255),
    username character varying(255)
);


ALTER TABLE users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY appointments ALTER COLUMN id SET DEFAULT nextval('appointments_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faculties ALTER COLUMN id SET DEFAULT nextval('faculties_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fields_of_study ALTER COLUMN id SET DEFAULT nextval('fields_of_study_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY news ALTER COLUMN id SET DEFAULT nextval('news_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY roles ALTER COLUMN id SET DEFAULT nextval('roles_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subject_groups ALTER COLUMN id SET DEFAULT nextval('subject_groups_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjects ALTER COLUMN id SET DEFAULT nextval('subjects_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: appointments; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO appointments VALUES (26, '2020-11-03 09:45:00', '2020-11-03 08:15:00', 22);
INSERT INTO appointments VALUES (27, '2020-11-10 09:45:00', '2020-11-10 08:15:00', 22);
INSERT INTO appointments VALUES (28, '2020-11-17 09:45:00', '2020-11-17 08:15:00', 22);
INSERT INTO appointments VALUES (29, '2020-11-24 09:45:00', '2020-11-24 08:15:00', 22);
INSERT INTO appointments VALUES (30, '2020-12-01 09:45:00', '2020-12-01 08:15:00', 22);
INSERT INTO appointments VALUES (31, '2020-12-08 09:45:00', '2020-12-08 08:15:00', 22);
INSERT INTO appointments VALUES (32, '2020-12-15 10:30:00', '2020-12-15 08:15:00', 22);
INSERT INTO appointments VALUES (53, '2020-12-20 14:00:00', '2020-12-20 10:00:00', 8);
INSERT INTO appointments VALUES (54, '2020-12-02 12:00:00', '2020-12-02 10:00:00', 3);
INSERT INTO appointments VALUES (55, '2020-12-01 12:00:00', '2020-12-01 10:00:00', 3);
INSERT INTO appointments VALUES (56, '2020-12-08 12:00:00', '2020-12-08 10:00:00', 3);
INSERT INTO appointments VALUES (57, '2020-12-09 12:00:00', '2020-12-09 10:00:00', 3);
INSERT INTO appointments VALUES (58, '2020-10-12 09:30:00', '2020-10-12 08:00:00', 1);
INSERT INTO appointments VALUES (59, '2020-10-19 09:30:00', '2020-10-19 08:00:00', 1);
INSERT INTO appointments VALUES (60, '2020-10-26 09:30:00', '2020-10-26 08:00:00', 1);
INSERT INTO appointments VALUES (61, '2020-11-02 09:30:00', '2020-11-02 08:00:00', 1);
INSERT INTO appointments VALUES (62, '2020-11-09 09:30:00', '2020-11-09 08:00:00', 1);
INSERT INTO appointments VALUES (63, '2020-11-16 09:30:00', '2020-11-16 08:00:00', 1);
INSERT INTO appointments VALUES (64, '2020-11-23 09:30:00', '2020-11-23 08:00:00', 1);
INSERT INTO appointments VALUES (65, '2020-11-30 09:30:00', '2020-11-30 08:00:00', 1);
INSERT INTO appointments VALUES (66, '2020-12-07 09:30:00', '2020-12-07 08:00:00', 1);
INSERT INTO appointments VALUES (67, '2020-12-14 09:30:00', '2020-12-14 08:00:00', 1);


--
-- Name: appointments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('appointments_id_seq', 67, true);


--
-- Data for Name: faculties; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO faculties VALUES (5, 'WB', '', 'Wydział Budownictwa', NULL);
INSERT INTO faculties VALUES (1, 'WIM', 'Bydgoszcz', 'Wydział Inżynierii Mechanicznej', '2021-02-10 10:00:00');
INSERT INTO faculties VALUES (3, 'WTiICh', 'Bydgoszcz', 'Wydział Technologii i Inżynierii Chemicznej', '2021-02-10 12:00:00');
INSERT INTO faculties VALUES (2, 'WTiE', 'Bydgoszcz, ul. Sylwestra Kaliskiego 7', 'Wydział Telekomunikacji, Informatyki i Elektrotechniki', '2021-01-21 11:00:00');


--
-- Name: faculties_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('faculties_id_seq', 6, true);


--
-- Data for Name: fields_of_study; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO fields_of_study VALUES (3, 0, 'Informatyka Stosowana', 1, 2);
INSERT INTO fields_of_study VALUES (4, 0, 'Informatyka Stosowana', 0, 2);
INSERT INTO fields_of_study VALUES (5, 1, 'Informatyka Stosowana', 0, 2);
INSERT INTO fields_of_study VALUES (6, 0, 'Elektrotechnika', 0, 2);
INSERT INTO fields_of_study VALUES (7, 0, 'Automatyka i Elektronika', 0, 2);
INSERT INTO fields_of_study VALUES (8, 0, 'Elektronika i telekomunikacja', 0, 2);
INSERT INTO fields_of_study VALUES (9, 0, 'Energetyka', 0, 2);
INSERT INTO fields_of_study VALUES (10, 0, 'Teleinformatyka ', 0, 2);
INSERT INTO fields_of_study VALUES (1, 0, 'Mechatronika', 0, 1);
INSERT INTO fields_of_study VALUES (11, 1, 'Teleinformatyka ', 0, 2);


--
-- Name: fields_of_study_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('fields_of_study_id_seq', 11, true);


--
-- Data for Name: groups_in_fields_of_study; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO groups_in_fields_of_study VALUES (23, 9);
INSERT INTO groups_in_fields_of_study VALUES (23, 1);
INSERT INTO groups_in_fields_of_study VALUES (24, 4);
INSERT INTO groups_in_fields_of_study VALUES (24, 10);
INSERT INTO groups_in_fields_of_study VALUES (25, 1);
INSERT INTO groups_in_fields_of_study VALUES (25, 3);
INSERT INTO groups_in_fields_of_study VALUES (26, 4);
INSERT INTO groups_in_fields_of_study VALUES (26, 10);
INSERT INTO groups_in_fields_of_study VALUES (26, 6);
INSERT INTO groups_in_fields_of_study VALUES (26, 8);
INSERT INTO groups_in_fields_of_study VALUES (26, 9);
INSERT INTO groups_in_fields_of_study VALUES (26, 7);
INSERT INTO groups_in_fields_of_study VALUES (27, 4);
INSERT INTO groups_in_fields_of_study VALUES (27, 10);
INSERT INTO groups_in_fields_of_study VALUES (28, 1);
INSERT INTO groups_in_fields_of_study VALUES (28, 4);
INSERT INTO groups_in_fields_of_study VALUES (28, 7);
INSERT INTO groups_in_fields_of_study VALUES (28, 10);
INSERT INTO groups_in_fields_of_study VALUES (29, 4);
INSERT INTO groups_in_fields_of_study VALUES (29, 10);
INSERT INTO groups_in_fields_of_study VALUES (30, 4);
INSERT INTO groups_in_fields_of_study VALUES (30, 1);
INSERT INTO groups_in_fields_of_study VALUES (31, 4);
INSERT INTO groups_in_fields_of_study VALUES (31, 10);
INSERT INTO groups_in_fields_of_study VALUES (22, 4);
INSERT INTO groups_in_fields_of_study VALUES (22, 5);
INSERT INTO groups_in_fields_of_study VALUES (22, 10);
INSERT INTO groups_in_fields_of_study VALUES (22, 11);
INSERT INTO groups_in_fields_of_study VALUES (8, 1);
INSERT INTO groups_in_fields_of_study VALUES (3, 1);
INSERT INTO groups_in_fields_of_study VALUES (1, 1);
INSERT INTO groups_in_fields_of_study VALUES (33, 1);
INSERT INTO groups_in_fields_of_study VALUES (33, 10);
INSERT INTO groups_in_fields_of_study VALUES (32, 10);


--
-- Data for Name: news; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO news VALUES (19, '<font face="Arial">Informujemy, &#380;e rejestracja na zaj&#281;cia obieralne rozpocznie si&#281; <b><u>10 lutego</u></b>. Godziny rozpocz&#281;cia dla poszczeg&#243;lnych wydzia&#322;&#243;w znajduj&#261; si&#281; poni&#380;ej:</font><div><span><br></span></div><div><span>Wydzia&#322; In&#380;ynierii Mechanicznej - </span><font color="#ff0000">10:00</font></div><hr><div><span>Wydzia&#322; Telekomunikacji, Informatyki i Elektrotechniki - </span><font color="#ff0000">11:00</font></div><hr><div>Wydzia&#322; Technologii i In&#380;ynierii Chemicznej<span>&#160;- </span><font color="#ff0000">12:00</font><p></p></div>', '2021-01-22 12:29:41.344629', 'Rozpoczęcie rejestracji', 1);


--
-- Name: news_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('news_id_seq', 24, true);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO roles VALUES (1, 'STUDENT');
INSERT INTO roles VALUES (2, 'TEACHER');
INSERT INTO roles VALUES (3, 'ADMIN');


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('roles_id_seq', 3, true);


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO students VALUES (111001, 7, 4, 1);
INSERT INTO students VALUES (111002, 7, 5, 1);
INSERT INTO students VALUES (111003, 7, 6, 1);
INSERT INTO students VALUES (111004, 7, 7, 1);
INSERT INTO students VALUES (111006, 7, 9, 1);
INSERT INTO students VALUES (111007, 7, 10, 1);
INSERT INTO students VALUES (111008, 7, 11, 1);
INSERT INTO students VALUES (111009, 7, 12, 1);
INSERT INTO students VALUES (0, 1, 13, NULL);
INSERT INTO students VALUES (110961, 7, 14, 4);
INSERT INTO students VALUES (111005, 7, 8, 1);
INSERT INTO students VALUES (0, 1, 19, NULL);
INSERT INTO students VALUES (111000, 7, 2, 4);
INSERT INTO students VALUES (111000, 7, 23, 4);
INSERT INTO students VALUES (111111, 3, 25, 6);
INSERT INTO students VALUES (0, 1, 26, NULL);


--
-- Data for Name: students_in_groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO students_in_groups VALUES (30, 8);
INSERT INTO students_in_groups VALUES (30, 2);
INSERT INTO students_in_groups VALUES (22, 14);
INSERT INTO students_in_groups VALUES (22, 2);
INSERT INTO students_in_groups VALUES (24, 23);
INSERT INTO students_in_groups VALUES (26, 14);
INSERT INTO students_in_groups VALUES (26, 23);
INSERT INTO students_in_groups VALUES (27, 14);
INSERT INTO students_in_groups VALUES (27, 23);
INSERT INTO students_in_groups VALUES (1, 5);
INSERT INTO students_in_groups VALUES (1, 6);
INSERT INTO students_in_groups VALUES (1, 7);
INSERT INTO students_in_groups VALUES (1, 9);
INSERT INTO students_in_groups VALUES (1, 10);
INSERT INTO students_in_groups VALUES (1, 11);
INSERT INTO students_in_groups VALUES (1, 12);
INSERT INTO students_in_groups VALUES (1, 8);
INSERT INTO students_in_groups VALUES (1, 2);


--
-- Data for Name: subject_groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO subject_groups VALUES (23, '01:30:00', 18, 'UTP', 1, 19, 21);
INSERT INTO subject_groups VALUES (24, '01:30:00', 45, 'UTP', 1, 21, 18);
INSERT INTO subject_groups VALUES (25, '03:00:00', 15, NULL, 1, 18, 15);
INSERT INTO subject_groups VALUES (26, '02:00:00', 90, 'UTP', 1, 13, 19);
INSERT INTO subject_groups VALUES (27, '01:30:00', 25, 'Teams, zdalnie', 1, 17, 16);
INSERT INTO subject_groups VALUES (28, '01:30:00', 15, 'UTP', 1, 16, 3);
INSERT INTO subject_groups VALUES (29, '01:30:00', 15, '', 1, 17, 20);
INSERT INTO subject_groups VALUES (30, NULL, 2, NULL, 3, 15, 20);
INSERT INTO subject_groups VALUES (32, NULL, 30, '', 0, 21, 21);
INSERT INTO subject_groups VALUES (33, NULL, 15, '', 1, 13, 16);
INSERT INTO subject_groups VALUES (22, '02:00:00', 15, 'Teams, kod: xxYYzz', 1, 12, 20);
INSERT INTO subject_groups VALUES (31, '01:30:00', 15, '', 1, 12, 20);
INSERT INTO subject_groups VALUES (8, '01:30:00', 1, 'UTP', 3, 18, 3);
INSERT INTO subject_groups VALUES (3, '02:00:00', 12, 'UTP', 1, 19, 16);
INSERT INTO subject_groups VALUES (1, '01:30:00', 14, 'UTP', 1, 12, 20);


--
-- Name: subject_groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('subject_groups_id_seq', 33, true);


--
-- Data for Name: subjects; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO subjects VALUES (12, 0, 2, '', 'Programowanie obiektowe', 54000000000000, '');
INSERT INTO subjects VALUES (13, 5, 0, '', 'Matematyka', 108000000000000, 'Polski');
INSERT INTO subjects VALUES (14, 3, 0, '', 'Technologie informacyjne', 54000000000000, 'Polski');
INSERT INTO subjects VALUES (15, 4, 2, '<p>Przedmiot zapoznaje z podstawowymi: wiedz&#261; i umiej&#281;tno&#347;ciami dotycz&#261;cymi wytwarzania oprogramowania. Obejmuje zwinny i i wodospadowego model wytwarzania oprogramowania oraz zwi&#261;zane z nimi procesy . Zmusza do wymaganej przez pracodawc&#243;w pracy zespo&#322;owej na wszystkich podstawowych etapach produkcji oprogramowania.</p><p><br></p>', 'Inżynieria oprogramowania', 54000000000000, 'Polski');
INSERT INTO subjects VALUES (16, 4, 0, '', 'Podstawy programowania', 108000000000000, 'Polski');
INSERT INTO subjects VALUES (17, 3, 1, '', 'Logika dla informatyków', 54000000000000, 'Polski');
INSERT INTO subjects VALUES (18, 5, 3, '', 'Robotyka', 108000000000000, 'English');
INSERT INTO subjects VALUES (19, 4, 1, '', 'Algorytmika', 54000000000000, 'Polski');
INSERT INTO subjects VALUES (20, 3, 0, '<font face="Arial">Lorem</font>', 'Podstawy elektroniki', 72000000000000, 'Polski');
INSERT INTO subjects VALUES (21, 3, 3, '', 'Podstawy grafiki komputerowej', 54000000000000, 'Polski');


--
-- Name: subjects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('subjects_id_seq', 21, true);


--
-- Data for Name: teachers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO teachers VALUES ('-', '-', 4, 1);
INSERT INTO teachers VALUES ('-', '-', 15, 2);
INSERT INTO teachers VALUES ('-', '-', 16, 2);
INSERT INTO teachers VALUES ('-', '-', 17, 2);
INSERT INTO teachers VALUES ('-', '-', 18, 2);
INSERT INTO teachers VALUES ('-', '-', 19, 2);
INSERT INTO teachers VALUES ('-', '-', 20, 2);
INSERT INTO teachers VALUES ('-', '-', 21, 2);
INSERT INTO teachers VALUES ('-', '-', 22, 2);
INSERT INTO teachers VALUES ('-', '-', 3, 2);


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO user_roles VALUES (1, 3);
INSERT INTO user_roles VALUES (2, 1);
INSERT INTO user_roles VALUES (3, 2);
INSERT INTO user_roles VALUES (4, 1);
INSERT INTO user_roles VALUES (5, 1);
INSERT INTO user_roles VALUES (6, 1);
INSERT INTO user_roles VALUES (7, 1);
INSERT INTO user_roles VALUES (8, 1);
INSERT INTO user_roles VALUES (9, 1);
INSERT INTO user_roles VALUES (10, 1);
INSERT INTO user_roles VALUES (11, 1);
INSERT INTO user_roles VALUES (12, 1);
INSERT INTO user_roles VALUES (4, 2);
INSERT INTO user_roles VALUES (13, 1);
INSERT INTO user_roles VALUES (14, 1);
INSERT INTO user_roles VALUES (15, 2);
INSERT INTO user_roles VALUES (16, 2);
INSERT INTO user_roles VALUES (17, 2);
INSERT INTO user_roles VALUES (18, 2);
INSERT INTO user_roles VALUES (19, 2);
INSERT INTO user_roles VALUES (20, 2);
INSERT INTO user_roles VALUES (21, 2);
INSERT INTO user_roles VALUES (22, 2);
INSERT INTO user_roles VALUES (19, 3);
INSERT INTO user_roles VALUES (19, 1);
INSERT INTO user_roles VALUES (23, 1);
INSERT INTO user_roles VALUES (25, 1);
INSERT INTO user_roles VALUES (26, 1);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users VALUES (3, 'jankow111@wp.pl', 'Jan Kowalski', true, '$2a$10$HBJbt7bN2TkseTD/GxieAOWILBdhTgHylUdV45lxUENdYhZpKuY8q', 'janek');
INSERT INTO users VALUES (4, 'adam1@utp.edu.pl', 'Adam 1 Nowak', true, '$2a$10$zbl5YInIrrXVIa79M9tcVOTl0psdA8rmikUrLilUOqqDuLRnYtkFa', 'adamek1');
INSERT INTO users VALUES (5, 'adam2@utp.edu.pl', 'Adam 2 Nowak', true, '$2a$10$Vt/YL7rGwUf.CXw6UK39S.loBvMQitUh3mibalGCrPQStrhX8DUzq', 'adamek2');
INSERT INTO users VALUES (6, 'adam3@utp.edu.pl', 'Adam 3 Nowak', true, '$2a$10$sAXsRrWC5P6LxqXJsoFdAOichi2UoqB7ZZQbKHfSbq.x/YsfurcJa', 'adamek3');
INSERT INTO users VALUES (7, 'adam4@utp.edu.pl', 'Adam 4 Nowak', true, '$2a$10$414Hku98d1R1oezRze8TyeEp1wADHOJnAkgUF4n57FoW6u1EL6iLW', 'adamek4');
INSERT INTO users VALUES (8, 'adam5@utp.edu.pl', 'Adam 5 Nowak', true, '$2a$10$IQvdKhqlKZL/vTT1uJ6rX.gPWvoMUuftg1bZ3AZNKSEWbxVkpC0Ei', 'adamek5');
INSERT INTO users VALUES (9, 'adam6@utp.edu.pl', 'Adam 6 Nowak', true, '$2a$10$V3xQ0aS1i6Ug7a/CIrocfeatfG.0tfon0INdy53eo4a9bNbefBzUO', 'adamek6');
INSERT INTO users VALUES (10, 'adam7@utp.edu.pl', 'Adam 7 Nowak', true, '$2a$10$TB4x.lffDN5lLzN71cU95.X7PPyVWt4sVUM4Mxcgcvf8bNCVmAXva', 'adamek7');
INSERT INTO users VALUES (11, 'adam8@utp.edu.pl', 'Adam 8 Nowak', true, '$2a$10$iM.wnMMwlRFsSyqGnGpbDO1ABoNF2wsAbKehwTzjtSBEwOZwCUPya', 'adamek8');
INSERT INTO users VALUES (12, 'adam9@utp.edu.pl', 'Adam 9 Nowak', true, '$2a$10$yx2GbucmErc6JgYY0ynqWe3cvIpG3UNVBsTqusrNTvF/RPKrNqxrO', 'adamek9');
INSERT INTO users VALUES (2, 'adamrewrwer@utp.edu.pl', 'Adam Kowalski', true, '$2a$10$dP7wD8zKRIKocaYJD5C6SuiO0hl3CRpDBgu4SDbZRGokjGVa9j9Dy', 'adamek');
INSERT INTO users VALUES (13, 'adam3123@utp.edu.pl', NULL, true, '$2a$10$tDfsrRG5v.wunjnrYxGZ6usPs/CgNTz4NhtUTyao12JroBilbsw6O', 'adamek323');
INSERT INTO users VALUES (14, 'mateusz75319@gmail.com', 'Mateusz Balcer', true, '$2a$10$4gHuzX93arfW9LO9yToIZeFfsAJfodGLxE7QzSEdRNFZ9nvxXfv.6', 'mateusz');
INSERT INTO users VALUES (15, 'arski@wp.pl', 'Kornel Kucharski', true, '$2a$10$.yKr69.0roA4AMUL5PPzieMUM2mHJe9pVHrpS8aSFGKd0iwNZEw8a', 'Kucharski');
INSERT INTO users VALUES (16, 'tko@wp.pl', 'Kajetan Rutkowski', true, '$2a$10$DfmNfCDSPORMr2xpvq6uSuQ3/ugZ.l6VKDsVmBjaI9pNcVUbRUBZG', 'Rutkowski');
INSERT INTO users VALUES (17, 'Wójcik@wp.pl', 'Oskar Wójcik', true, '$2a$10$LKMg2ou.E3vA9.6pElUzi.PYkmQMtb5iiH6z/s0gWbLlQrTIExzIy', 'Wójcik');
INSERT INTO users VALUES (18, 'Nowak@wp.pl', 'Andrzej Nowak', true, '$2a$10$c0QnPdbHnmxSQUSxW7jEQ.VfrnAK7c5Oxd6n9xcu1bzQF6tW1Uva.', 'Nowak');
INSERT INTO users VALUES (19, 'Sawicki@wp.pl', 'Martin Sawicki', true, '$2a$10$sbO670dZEbKVLaMf1jY4VOf7eIPNCXtB36VU4.eujbqIYby/O/4sK', 'Sawicki');
INSERT INTO users VALUES (21, 'Wróblewski@wp.pl', 'Daniel Wróblewski', true, '$2a$10$A7z10Vz/PSzEaZ/MXYuHjuhqEqZJrw4fKZmlzy2kbfQLWsibvhVFW', 'Wróblewski');
INSERT INTO users VALUES (22, 'Krawczyk@wp.pl', 'Jan Krawczyk', true, '$2a$10$zhaTlddo7au87nNY3.rNQeBJRrHMxRrA0RBrCs/h7sgA4VzP/XnEa', 'Krawczyk');
INSERT INTO users VALUES (23, 'mati@wp.pl', 'Mateusz Gombrowicz', true, '$2a$10$yC7TNBui1b/HiG40mADC0uAEnXKZ4OTlFxV2Swrjp3QcIGXvyKsxK', 'mati1234');
INSERT INTO users VALUES (25, 'student@wp.pl', 'Test Student', true, '$2a$10$gnMIdNamr2S3Ec/fAZ42qupY4QOZWiSC8KC6w4vONTS/Rm/gMPuiO', 'testStudent');
INSERT INTO users VALUES (26, 'jak@wp.pl', NULL, true, '$2a$10$IWolHb0pJpdaBRfhcUjOkuACW4EF7K3KamsPEDqDfKqi1zStEUZye', 'jakubek');
INSERT INTO users VALUES (1, 'admin@utp.edu.pl', 'Administrator', true, '$2a$10$Y.M7xk0.Vozm2ADunmrE1ejZWkxzMV4LKBl5LIiMLjrr94eF.GTAC', 'admin1');
INSERT INTO users VALUES (20, 'Cieślak@wp.pl', 'Radosław Cieślak', true, '$2a$10$wrORdkf4ZWOhCCi675JJlOvYDVzz8zJHRqep1OueIQ2nUEglxfXcO', 'radekCie');


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 26, true);


--
-- Name: appointments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY appointments
    ADD CONSTRAINT appointments_pkey PRIMARY KEY (id);


--
-- Name: faculties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY faculties
    ADD CONSTRAINT faculties_pkey PRIMARY KEY (id);


--
-- Name: fields_of_study_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fields_of_study
    ADD CONSTRAINT fields_of_study_pkey PRIMARY KEY (id);


--
-- Name: news_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY news
    ADD CONSTRAINT news_pkey PRIMARY KEY (id);


--
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: students_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students
    ADD CONSTRAINT students_pkey PRIMARY KEY (user_id);


--
-- Name: subject_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subject_groups
    ADD CONSTRAINT subject_groups_pkey PRIMARY KEY (id);


--
-- Name: subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (id);


--
-- Name: teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (user_id);


--
-- Name: uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: fk2lgwxrrjrc8ai9tof9pp9wwxn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups_in_fields_of_study
    ADD CONSTRAINT fk2lgwxrrjrc8ai9tof9pp9wwxn FOREIGN KEY (fields_of_study_id) REFERENCES fields_of_study(id);


--
-- Name: fk3l2b7cqinsrtpfep6bj0bvfps; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY appointments
    ADD CONSTRAINT fk3l2b7cqinsrtpfep6bj0bvfps FOREIGN KEY (group_id) REFERENCES subject_groups(id);


--
-- Name: fk3qvva8ftw201mxkeuirniflgb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY news
    ADD CONSTRAINT fk3qvva8ftw201mxkeuirniflgb FOREIGN KEY (author_id) REFERENCES users(id);


--
-- Name: fk686k2txbygqw3dc80j660sn8f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students_in_groups
    ADD CONSTRAINT fk686k2txbygqw3dc80j660sn8f FOREIGN KEY (students_id) REFERENCES students(user_id);


--
-- Name: fk7y6q52cpc1q6n92xuocwlsq44; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students_in_groups
    ADD CONSTRAINT fk7y6q52cpc1q6n92xuocwlsq44 FOREIGN KEY (subject_groups_id) REFERENCES subject_groups(id);


--
-- Name: fk8mpjitw0st399y1p8gfmgvx28; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subject_groups
    ADD CONSTRAINT fk8mpjitw0st399y1p8gfmgvx28 FOREIGN KEY (teacher_user_id) REFERENCES teachers(user_id);


--
-- Name: fkb8dct7w2j1vl1r2bpstw5isc0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY teachers
    ADD CONSTRAINT fkb8dct7w2j1vl1r2bpstw5isc0 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fkd3iibvuntoah4g1qe98guwffm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY fields_of_study
    ADD CONSTRAINT fkd3iibvuntoah4g1qe98guwffm FOREIGN KEY (faculty_id) REFERENCES faculties(id);


--
-- Name: fkdt1cjx5ve5bdabmuuf3ibrwaq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students
    ADD CONSTRAINT fkdt1cjx5ve5bdabmuuf3ibrwaq FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fke7qly32tnvkcy83q84l9hvatc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups_in_fields_of_study
    ADD CONSTRAINT fke7qly32tnvkcy83q84l9hvatc FOREIGN KEY (subject_groups_id) REFERENCES subject_groups(id);


--
-- Name: fkevp0h721cfyn2rhobgpcnlqsd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subject_groups
    ADD CONSTRAINT fkevp0h721cfyn2rhobgpcnlqsd FOREIGN KEY (subject_id) REFERENCES subjects(id);


--
-- Name: fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES roles(id);


--
-- Name: fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fkisraolv4ig9ougjcj4w4co902; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students
    ADD CONSTRAINT fkisraolv4ig9ougjcj4w4co902 FOREIGN KEY (field_of_study_id) REFERENCES fields_of_study(id);


--
-- Name: fkt9rth51e1sfx7dox4m60qnjh6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY teachers
    ADD CONSTRAINT fkt9rth51e1sfx7dox4m60qnjh6 FOREIGN KEY (faculty_id) REFERENCES faculties(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

