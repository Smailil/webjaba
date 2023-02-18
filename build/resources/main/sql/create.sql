DROP TABLE IF EXISTS employees CASCADE;
CREATE TABLE employees (
    employee_id serial PRIMARY KEY,
    name text NOT NULL,
    home_address text NOT NULL,
    day_of_birth date NOT NULL,
    education text NOT NULL,
    position text NOT NULL,
    length_of_service integer NOT NULL
);

DROP TABLE IF EXISTS position_history CASCADE;
CREATE TABLE position_history (
    id serial PRIMARY KEY,
    employee_id integer NOT NULL REFERENCES employees (employee_id) ON DELETE CASCADE,
    position text NOT NULL,
    date_of_attainment date NOT NULL
);

DROP TABLE IF EXISTS projects CASCADE;
CREATE TABLE projects (
    project_id serial PRIMARY KEY,
    name text NOT NULL,
    start_date date NOT NULL,
    end_date date
);

DROP TABLE IF EXISTS employees_on_projects CASCADE;
CREATE TABLE employees_on_projects (
    id serial PRIMARY KEY,
    project_id integer NOT NULL REFERENCES projects (project_id) ON DELETE CASCADE,
    employee_id integer NOT NULL REFERENCES employees (employee_id) ON DELETE CASCADE,
    role text NOT NULL
);

DROP TABLE IF EXISTS payment_history CASCADE;
CREATE TABLE payment_history (
    payment_id serial PRIMARY KEY,
    employee_id integer NOT NULL REFERENCES employees (employee_id) ON DELETE CASCADE,
    type text NOT NULL,
    payment_amount money NOT NULL,
    date date NOT NULL,
    is_award boolean NOT NULL
);

DROP TABLE IF EXISTS disbursement_policies CASCADE;
CREATE TABLE disbursement_policies (
    policies_id serial PRIMARY KEY,
    type text NOT NULL,
    payment_amount money NOT NULL
);

