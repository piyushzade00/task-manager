import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const SignUpPage = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    profilePhoto: null,
  });

  const [error, setError] = useState(null);

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, type, files } = e.target;
    if (type === "file") {
      setFormData({ ...formData, [name]: files[0] });
      console.log(files[0]);
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.name || !formData.email || !formData.password) {
      alert("Please fill in all fields.");
      return;
    }

    const formDataToSend = new FormData();

    formDataToSend.append("userName", formData.name);
    formDataToSend.append("email", formData.email);
    formDataToSend.append("password", formData.password);
    formDataToSend.append("profilePhoto", formData.profilePhoto);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/users/create-user",
        formDataToSend,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log(response.data);
      if (response.status === 200) {
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("user", JSON.stringify(response.data));
        navigate("/home");
      } else {
        setError(response.data.message);
      }
      navigate("/home");
    } catch (error) {
      if (error.response) {
        setError(error.response.data.message);
        console.error("Error from backend:", error.response.data.message);
      } else if (error.request) {
        setError("No response from server. Please try again later.");
        console.error("No response from server:", error.request);
      } else {
        setError("Error setting up request. Please try again later.");
        console.error("Error setting up request:", error.message);
      }
    } finally {
      if (error === null) {
        navigate("/home");
      }
    }
  };

  return (
    <>
      <div className="page-container">
        <div className="signup-container">
          <h2 className="signup-header">Sign Up</h2>
          <p className="signup-description">
            Create an account to get started.
          </p>

          <form className="signup-form" onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="name">User Name</label>
              <input
                type="text"
                id="name"
                name="name"
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                type="email"
                id="email"
                name="email"
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                name="password"
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="profile-photo">Profile Photo</label>
              <input
                type="file"
                id="profile-photo"
                name="profilePhoto"
                accept="image/*"
                onChange={handleChange}
              />
            </div>

            <button type="submit" className="signup-button">
              Sign Up
            </button>
            <p className="error-message">{error}</p>
            <p className="signup-footer">
              Already have an account? <a href="/login">Log In</a>
            </p>
          </form>
        </div>
      </div>
    </>
  );
};

export default SignUpPage;
