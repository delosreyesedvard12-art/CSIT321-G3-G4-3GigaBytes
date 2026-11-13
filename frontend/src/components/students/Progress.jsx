import React, { useState, useEffect } from 'react';
import ProgressBar from './ProgressBar';
function Progress() {
  const [progress, setProgress] = useState(0);
  useEffect(() => {
    const timer = setInterval(() => {
      setProgress(prev => (prev < 100 ? prev + 1 : 100));
    }, 100);
    return () => clearInterval(timer);
  }, []);
  return <ProgressBar progress={progress} />;
}
export default Progress;
