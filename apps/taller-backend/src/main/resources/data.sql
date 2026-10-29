INSERT INTO estudiantes (nombre, email, curso)
SELECT 'Valentina Rojas', 'valentina@gmail.com', 'diseño de interfaces'
WHERE NOT EXISTS (SELECT 1 FROM estudiantes WHERE email = 'valentina@gmail.com');

INSERT INTO estudiantes (nombre, email, curso)
SELECT 'Camila Torres', 'camila.torres.2026@example.com', 'Desarrollo web'
WHERE NOT EXISTS (SELECT 1 FROM estudiantes WHERE email = 'camila.torres.2026@example.com');

INSERT INTO estudiantes (nombre, email, curso)
SELECT 'Diego Herrera', 'diego.herrera.2026@example.com', 'Bases de datos'
WHERE NOT EXISTS (SELECT 1 FROM estudiantes WHERE email = 'diego.herrera.2026@example.com');

INSERT INTO estudiantes (nombre, email, curso)
SELECT 'Sofía Navarro', 'sofia.navarro.2026@example.com', 'Diseño UX/UI'
WHERE NOT EXISTS (SELECT 1 FROM estudiantes WHERE email = 'sofia.navarro.2026@example.com');

INSERT INTO estudiantes (nombre, email, curso)
SELECT 'Andrés Molina', 'andres.molina.2026@example.com', 'Java y Spring Boot'
WHERE NOT EXISTS (SELECT 1 FROM estudiantes WHERE email = 'andres.molina.2026@example.com');

INSERT INTO estudiantes (nombre, email, curso)
SELECT 'Mariana Vega', 'mariana.vega.2026@example.com', 'Ciberseguridad'
WHERE NOT EXISTS (SELECT 1 FROM estudiantes WHERE email = 'mariana.vega.2026@example.com');
