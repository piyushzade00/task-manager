import React, { useState, useEffect } from "react";
import axios from "axios";
import TaskItem from "./TaskItem";

const TaskList = ({ refetchTrigger, onUpdate, onDelete }) => {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchTasks = async () => {
      const userId = JSON.parse(localStorage.getItem("user")).userId;
      if (!userId) {
        setError("User ID not found. Please log in again.");
        return;
      }
      try {
        const response = await axios.get(
          "http://localhost:8080/api/tasks/get-all-tasks-for-user/" + userId,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );

        setTasks(response.data);
        setError(null); // Clear any previous errors
      } catch (error) {
        setTasks([]); // Clear tasks on error
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
      }
    };
    fetchTasks();
  }, [refetchTrigger]);

  return (
    <>
      <div>
        <h2 className="your-tasks-header">Your Tasks</h2>
        {error && <p>{error}</p>}
        {tasks.length === 0 ? (
          <p>No tasks available.</p>
        ) : (
          <table className="task-table">
            <thead>
              <tr>
                <th className="table-header">Description</th>
                <th className="table-header">Status</th>
                <th className="table-header">Priority</th>
                <th className="table-header">Deadline</th>
                <th className="table-header"></th>
                <th className="table-header"></th>
              </tr>
            </thead>
            <tbody>
              {tasks.map((task) => (
                <TaskItem
                  key={task.id}
                  task={task}
                  onUpdate={onUpdate}
                  onDelete={onDelete}
                />
              ))}
            </tbody>
          </table>
        )}
      </div>
    </>
  );
};

export default TaskList;
