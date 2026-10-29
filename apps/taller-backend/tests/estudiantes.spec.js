const { test, expect } = require("@playwright/test");
const { randomUUID } = require("node:crypto");

const API_URL = "http://localhost:8080/api/estudiantes";

test("crear un estudiante y consultar su perfil completo", async ({ page, request }) => {
  const estudiante = {
    nombre: "QA Playwright",
    email: `qa-${randomUUID()}@example.com`,
    curso: "Pruebas E2E",
  };
  let estudianteId;

  try {
    await page.goto("/");
    await page.getByLabel("Nombre completo").fill(estudiante.nombre);
    await page.getByLabel("Correo electrónico").fill(estudiante.email);
    await page.getByLabel("Curso", { exact: true }).fill(estudiante.curso);

    const respuestaCreacion = page.waitForResponse(
      (response) =>
        response.url() === API_URL &&
        response.request().method() === "POST"
    );
    await page.getByRole("button", { name: "Guardar estudiante" }).click();

    const response = await respuestaCreacion;
    expect(response.status()).toBe(201);
    estudianteId = (await response.json()).id;

    const tarjeta = page.getByRole("button", {
      name: `Ver perfil de ${estudiante.nombre}`,
    });
    await expect(tarjeta).toBeVisible();
    await expect(tarjeta).toContainText(estudiante.curso);

    await tarjeta.click();
    const modal = page.getByRole("dialog");
    await expect(modal).toBeVisible();
    await expect(modal).toContainText(estudiante.email);
    await expect(modal).toContainText(estudiante.curso);
  } finally {
    if (estudianteId !== undefined && estudianteId !== null) {
      const respuestaEliminacion = await request.delete(`${API_URL}/${estudianteId}`);
      expect(respuestaEliminacion.status()).toBe(204);
    }
  }
});
