const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

//Access token stored in MEMORY (not localStorage)
let accessToken: string | null = null;

// Function to set access token (called by AuthProvider)
export const setAccessToken = (token: string | null): void => {
  accessToken = token;
};

// Function to get access token
export const getAccessToken = (): string | null => {
  return accessToken;
};

// Callback for handling banned user (set by AuthProvider)
let onUserBannedCallback: (() => void) | null = null;

// Function to set the banned callback (called by AuthProvider)
export const setOnUserBanned = (callback: () => void): void => {
  onUserBannedCallback = callback;
};

// Build headers with Authorization if token exists
const getHeaders = (skipContentType = false): HeadersInit => {
  const headers: HeadersInit = {
    'Accept': 'application/json',
  };

  if (!skipContentType) {
    headers['Content-Type'] = 'application/json';
  }

  if (accessToken) {
    headers['Authorization'] = `Bearer ${accessToken}`;
  }

  return headers;
};

// Generic fetch wrapper
const request = async <T>(
  url: string,
  options: RequestInit = {}
): Promise<T> => {
  const response = await fetch(`${API_BASE_URL}${url}`, options);

  // Handle non-2xx responses
  if (!response.ok) {
    const error = await response.json().catch(() => ({
      message: response.statusText,
    }));

    // Check if user is banned
    if (
      error.message &&
      typeof error.message === 'string' &&
      error.message.toLowerCase().includes('banned')
    ) {
      if (onUserBannedCallback) {
        onUserBannedCallback();
      }
    }

    throw error;
  }

  // Handle 204 No Content
  if (response.status === 204) {
    return {} as T;
  }

  return response.json();
};

// HTTP methods
export const http = {
  get: <T>(url: string, options?: RequestInit): Promise<T> => {
    return request<T>(url, {
      ...options, method: 'GET',
      headers: {
        ...getHeaders(),
        ...options?.headers,
      },
    });
  },

  post: <T>(url: string, data?: unknown, options?: RequestInit): Promise<T> => {
    const isFormData = data instanceof FormData;

    return request<T>(url, {
      ...options,
      method: 'POST',
      body: isFormData ? data : JSON.stringify(data),
      headers: {
        ...getHeaders(isFormData),
        ...options?.headers,
      },
    });
  },

  put: <T>(url: string, data?: unknown, options?: RequestInit): Promise<T> => {
    return request<T>(url, {
      ...options,
      method: 'PUT',
      body: JSON.stringify(data),
      headers: {
        ...getHeaders(),
        ...options?.headers,
      },
    });
  },

  delete: <T>(url: string, data?: unknown, options?: RequestInit): Promise<T> => {
    return request<T>(url, {
      ...options,
      method: 'DELETE',
      ...(data ? { body: JSON.stringify(data) } : {}),
      headers: {
        ...getHeaders(),
        ...options?.headers,
      },
    });
  },
};