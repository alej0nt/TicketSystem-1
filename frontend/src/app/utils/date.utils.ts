/**
 * Formatea una fecha ISO a formato legible
 * @param isoDate - Fecha en formato ISO (2025-10-15T10:54:15.245292)
 * @returns Fecha formateada (15 oct 2025, 2:30 PM)
 */
export function formatDate(isoDate: string): string {
  if (!isoDate) return '';
  
  const date = new Date(isoDate);
  
  return date.toLocaleString('es-ES', {
    day: '2-digit',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    hour12: true
  });
}