import React from "react";

const TaskItem = ({ task, onUpdate, onDelete }) => {
  return (
    <tr className="task-row" key={task.taskId}>
      <td className="task-description">{task.description}</td>
      <td className="task-data">{task.status}</td>
      <td className="task-data">{task.priority}</td>
      <td className="task-data">{task.deadline}</td>
      <td className="task-data">
        <button className="edit-button" onClick={(e) => onUpdate(task)}>
          Edit
        </button>
      </td>
      <td className="task-data">
        <button className="delete-button" onClick={() => onDelete(task)}>
          Delete
        </button>
      </td>
    </tr>
  );
};

export default TaskItem;
