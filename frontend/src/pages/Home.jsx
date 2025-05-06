import axios from "axios";
import React from "react";
import { useState, useEffect } from "react";
import TaskList from "../components/TaskList";
import PageHeader from "../components/PageHeader";

const Home = () => {
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [editTaskId, setEditTaskId] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setIsLoading(false);
    }, 1000);

    return () => clearTimeout(timer); // Cleanup
  }, []);

  const [formData, setFormData] = useState({
    description: "",
    status: "incomplete",
    priority: "low",
    deadline: "",
  });

  const [refetchTrigger, setRefetchTrigger] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.description.trim()) {
      setError("Description cannot be empty.");
      return;
    }

    const formattedDeadline = formData.deadline
      ? `${formData.deadline}T00:00:00`
      : null;

    const finalData = {
      ...formData,
      deadline: formattedDeadline,
    };

    finalData.status = formData.status.toUpperCase();
    finalData.priority = formData.priority.toUpperCase();
    finalData.userId = JSON.parse(localStorage.getItem("user")).userId;

    const url = isEditing
      ? `http://localhost:8080/api/tasks/update-task/${editTaskId}`
      : `http://localhost:8080/api/tasks/create-task`;

    try {
      const response = await axios.post(url, finalData, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });
      console.log(response.data);
      if (response.status === 200) {
        console.log(`${isEditing ? "Updated" : "Created"} successfully`);
        setFormData({
          description: "",
          status: "incomplete",
          priority: "low",
          deadline: "",
        });

        setError(null);
        setIsEditing(false);
        setEditTaskId(null);
        setRefetchTrigger((prev) => !prev);
      } else {
        setError(response.data.message);
      }
    } catch (error) {
      if (error.response?.status === 401) {
        setError("Unauthorized. Please log in again.");
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        window.location.href = "/login";
      } else if (error.response) {
        setError(error.response.data.message || "Something went wrong");
        console.error("Error from backend:", error.response.data.message);
      } else if (error.request) {
        setError("No response from server. Please try again later.");
        console.error("No response from server:", error.request);
      } else {
        setError("An unexpected error occurred. Please try again.");
        console.error("Unexpected error:", error.message);
      }
    }
  };

  const handleUpdate = async (t) => {
    const updatedData = {
      taskId: t.taskId,
      description: t.description,
      status: t.status.toLowerCase(),
      priority: t.priority.toLowerCase(),
      deadline: t.deadline ? t.deadline.split("T")[0] : "",
      userId: t.userId,
    };

    setFormData(updatedData);
    console.log("Updated data:", updatedData);

    setIsEditing(true);
    setEditTaskId(t.taskId);
  };

  const handleDelete = async (t) => {
    console.log("Delete button clicked for task:", t.taskId);

    const moveTaskToBin = confirm(
      `Are you sure you want to move the task "${t.description}" to bin?`
    );

    if (moveTaskToBin) {
      try {
        const response = await axios.delete(
          `http://localhost:8080/api/tasks/soft-delete-task/${t.taskId}`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        console.log(response.data);
        if (response.status === 200) {
          console.log("Task deleted successfully");
          setRefetchTrigger((prev) => !prev);
        } else {
          setError(response.data.message);
        }
      } catch (error) {
        if (error.response?.status === 401) {
          setError("Unauthorized. Please log in again.");
          localStorage.removeItem("token");
          localStorage.removeItem("user");
          window.location.href = "/login";
        } else if (error.response) {
          setError(error.response.data.message || "Something went wrong");
          console.error("Error from backend:", error.response.data.message);
        } else if (error.request) {
          setError("No response from server. Please try again later.");
          console.error("No response from server:", error.request);
        } else {
          setError("An unexpected error occurred. Please try again.");
          console.error("Unexpected error:", error.message);
        }
      }
    }
  };

  if (isLoading) {
    return (
      <div className="loader-container">
        <div className="spinner"></div>
        <p>Loading...</p>
      </div>
    );
  }

  return (
    <>
      <PageHeader />
      <div className="home-page-container">
        <div className="home-container">
          <h2 className="home-header">Log a new task</h2>

          <form className="new-task-form" onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="description">Description*</label>
              <input
                type="text"
                id="description"
                name="description"
                value={formData.description}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="status">Status</label>
              <select
                id="status"
                name="status"
                value={formData.status}
                onChange={handleChange}
              >
                <option value="incomplete">Incomplete</option>
                <option value="ongoing">In Progress</option>
                <option value="completed">Completed</option>
              </select>
            </div>

            <div className="form-group">
              <label htmlFor="priority">Priority</label>
              <select
                id="priority"
                name="priority"
                value={formData.priority}
                onChange={handleChange}
              >
                <option value="low">Low</option>
                <option value="medium">Medium</option>
                <option value="high">High</option>
              </select>
            </div>

            <div className="form-group">
              <label htmlFor="deadline">Deadline</label>
              <input
                type="date"
                id="deadline"
                name="deadline"
                value={formData.deadline}
                onChange={handleChange}
              />
            </div>

            <div className="button-group">
              <button
                type="submit"
                id="log-task-button"
                className="log-task-button"
              >
                {isEditing ? "Update Task" : "Log Task"}
              </button>
              {isEditing && (
                <button
                  type="button"
                  className="cancel-edit-button"
                  onClick={() => {
                    setIsEditing(false);
                    setEditTaskId(null);
                    setFormData({
                      description: "",
                      status: "incomplete",
                      priority: "low",
                      deadline: "",
                    });
                  }}
                >
                  Cancel Edit
                </button>
              )}
            </div>
            <p className="error-message">{error}</p>
          </form>
        </div>
        <div className="task-list-container">
          <TaskList
            refetchTrigger={refetchTrigger}
            onUpdate={handleUpdate}
            onDelete={handleDelete}
          />
        </div>
      </div>
    </>
  );
};

export default Home;
