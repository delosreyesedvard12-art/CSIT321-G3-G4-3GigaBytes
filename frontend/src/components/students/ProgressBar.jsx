import React from 'react';
import './StudentStyles.css';
function ProgressBar({ progress }) {
    return (
        <div className="progress-bar">
            <div className="progress-bar-fill" style={{ width: `${progress}%` }}></div>
            <span className="progress-text">{progress}%</span>
        </div>
    );
}

export default ProgressBar;