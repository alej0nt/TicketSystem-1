export const BUTTON_STYLES = {
  base: 'font-medium text-sm px-5 py-2.5 text-center transition-all duration-300',
  
  sizes: {
    sm: 'px-3 py-1.5 text-xs',
    md: 'px-5 py-2.5 text-sm',
    lg: 'px-7 py-3 text-base'
  },
  
  rounded: {
    none: 'rounded-none',
    sm: 'rounded-sm',
    md: 'rounded-md',
    lg: 'rounded-lg',
    full: 'rounded-full'
  },
  
  variants: {
    solid: {
      blue: 'text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800',
      purple: 'focus:outline-none text-white bg-purple-700 hover:bg-purple-800 focus:ring-4 focus:ring-purple-300 dark:bg-purple-600 dark:hover:bg-purple-700 dark:focus:ring-purple-900',
      red: 'focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900'
    },
    outline: {
      blue: '',
      purple: '',
      red: ''
    },
    gradient: {
      blue: '',
      purple: '',
      red: ''
    }
  },
  
  shadows: {
    blue: 'shadow-lg shadow-blue-500/50 dark:shadow-lg dark:shadow-blue-800/80',
    purple: '',
    red: ''
  },
  
  effects: {
    hover: 'hover:-translate-y-1 hover:shadow-xl'
  },
  
  disabled: 'opacity-50 cursor-not-allowed pointer-events-none'
} as const;