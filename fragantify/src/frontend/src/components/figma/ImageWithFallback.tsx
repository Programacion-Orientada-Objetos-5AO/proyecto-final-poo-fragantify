import { useState } from "react";

const ERROR_IMG_SRC =
  "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iODgiIGhlaWdodD0iODgiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgc3Ryb2tlPSIjMDAwIiBzdHJva2UtbGluZWpvaW49InJvdW5kIiBvcGFjaXR5PSIuMyIgZmlsbD0ibm9uZSIgc3Ryb2tlLXdpZHRoPSIzLjciPjxyZWN0IHg9IjE2IiB5PSIxNiIgd2lkdGg9IjU2IiBoZWlnaHQ9IjU2IiByeD0iNiIvPjxwYXRoIGQ9Im0xNiA1OCAxNi0xOCAzMiAzMiIvPjxjaXJjbGUgY3g9IjUzIiBjeT0iMzUiIHI9IjciLz48L3N2Zz4KCg==";

interface ImageWithFallbackProps extends React.ImgHTMLAttributes<HTMLImageElement> {
  fallbackUrls?: string[];
}

export function ImageWithFallback({ fallbackUrls, src, alt, style, className, ...rest }: ImageWithFallbackProps) {
  const [didError, setDidError] = useState(false);
  const [fallbackIndex, setFallbackIndex] = useState(0);

  const resolveSource = () => {
    if (!didError) {
      return src;
    }
    const nextFallback = fallbackUrls?.[fallbackIndex];
    if (nextFallback) {
      return nextFallback;
    }
    return ERROR_IMG_SRC;
  };

  const handleError = () => {
    if (fallbackUrls && fallbackIndex < fallbackUrls.length) {
      setFallbackIndex((prev) => prev + 1);
      setDidError(true);
    } else {
      setDidError(true);
    }
  };

  const currentSrc = resolveSource();

  if (currentSrc === ERROR_IMG_SRC) {
    return (
      <div
        className={`inline-block bg-gray-100 text-center align-middle ${className ?? ""}`}
        style={style}
      >
        <div className="flex items-center justify-center w-full h-full">
          <img src={ERROR_IMG_SRC} alt="Error loading image" {...rest} data-original-url={src} />
        </div>
      </div>
    );
  }

  return (
    <img
      src={currentSrc}
      alt={alt}
      className={className}
      style={style}
      {...rest}
      onError={handleError}
    />
  );
}
