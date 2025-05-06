import React from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const LoginPage = () => {
  const [formData, setFormData] = React.useState({
    email: "",
    password: "",
  });
  const [error, setError] = React.useState(null);

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.email || !formData.password) {
      setError("Please fill in all fields.");
      return;
    }

    const dataToSend = {
      email: formData.email,
      password: formData.password,
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/api/users/login-user",
        dataToSend,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      console.log(response.data);
      if (response.status === 200) {
        localStorage.setItem("token", response.data.jwtToken);
        localStorage.setItem("user", JSON.stringify(response.data));
        navigate("/home");
      } else {
        setError(response.data.message);
      }
    } catch (error) {
      if (error.response) {
        setError(error.response.data.message);
        console.error("Error from backend:", error.response.data.message);
      } else if (error.request) {
        setError("No response from server. Please try again later.");
        console.error("No response from server:", error.request);
      } else {
        setError("An unexpected error occurred. Please try again.");
        console.error("Unexpected error:", error.message);
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
        <div className="login-container">
          <h2 className="login-header">Login</h2>
          <p className="login-description">
            Log in to your account to get started.
          </p>

          <form className="login-form" onSubmit={handleSubmit}>
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

            <button type="submit" className="login-button">
              Log In
            </button>
            <p className="error-message">{error}</p>
          </form>
        </div>
      </div>
    </>
  );
};

export default LoginPage;
