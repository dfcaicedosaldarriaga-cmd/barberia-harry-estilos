import React from "react";

/**
 * Componente Alert - Cuadro de retroalimentación accesible para el usuario.
 * Utiliza los roles semánticos role="status" y aria-live="polite" para que los lectores
 * de pantalla anuncien mensajes de éxito o advertencia dinámicamente sin interrumpir.
 * 
 * @component
 * @param {Object} props
 * @param {string} props.message Texto de la notificación a desplegar.
 * @param {"success"|"error"|"warning"|"info"} [props.type="success"] Nivel o categoría del aviso.
 * @returns {JSX.Element|null} Elemento de alerta o null si no hay mensaje activo.
 */
export default function Alert({ message, type = "success" }) {
  if (!message) return null;

  return (
    <p className={`alert alert--${type}`} role="status" aria-live="polite">
      {message}
    </p>
  );
}
