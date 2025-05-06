import React, { useEffect, useState } from "react";
import axios from "axios";

const PageHeader = () => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [user, setUser] = useState({
    userName: null,
    email: null,
    profilePhotoPath: null,
  });
  const [error, setError] = useState(null);

  const toggleDropdown = () => {
    setIsDropdownOpen((prev) => !prev);
  };

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    } else {
      window.location.href = "/login"; // Redirect to login page if user is not found
    }
  }, []);

  const handleLogout = async () => {
    try {
      console.log(user.email);
      const response = await axios.post(
        `http://localhost:8080/api/users/logout-user/${user.email}`,
        {},
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      console.log("Is Logout - " + response.data);
      if (response.status === 200) {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        window.location.href = "/login"; // Redirect to login page after logout
      }
    } catch (error) {
      if (error.response?.status === 401) {
        setError("Unauthorized. Please log in again.");
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        window.location.href = "/login";
      } else if (error.response) {
        setError(error.response.data.message);
      } else {
        setError("An unexpected error occurred. Please try again.");
      }
    }
  };
  return (
    <>
      <header className="header">
        <div className="title">Task Manager</div>
        <div className="profile-section">
          <button className="profile-button" onClick={toggleDropdown}>
            <img
              src={
                user.profilePhotoPath
                  ? `http://localhost:8080/images/${user.profilePhotoPath}`
                  : "https://i.pravatar.cc/40?img=3" // fallback if null
              }
              onError={(e) => {
                e.target.onerror = null; // Prevent infinite loop
                e.target.src = "https://i.pravatar.cc/40?img=3"; // Fallback image
              }}
              alt="Profile"
              className="avatar"
            />
            <span className="username">{user.userName || "Guest"}</span>
            <span className="chevron">&#9662;</span>
          </button>

          {isDropdownOpen && (
            <div className="dropdown">
              <button onClick={handleLogout}>Logout</button>
            </div>
          )}
        </div>
      </header>
    </>
  );
};

export default PageHeader;
