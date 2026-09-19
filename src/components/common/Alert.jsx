import React from "react";

export default function Alert({ message, type = "success" }) {
  if (!message) return null;

  return (
    <p className={`alert alert--${type}`} role="status" aria-live="polite">
      {message}
    </p>
  );
}
