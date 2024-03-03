// import React, { useState } from 'react';
// import { FaBell } from 'react-icons/fa';

// const NotificationBell = () => {
//   const [unreadCount, setUnreadCount] = useState(3);

//   const [showNotifications, setShowNotifications] = useState(false);

//   const toggleNotifications = () => {
//     setShowNotifications(!showNotifications);

//     if (showNotifications) {
//       setUnreadCount(0);
//     }
//   };

//   return (
//     <div className="notification-bell">
//       <div onClick={toggleNotifications} className="notification-bell-icon">
//         <FaBell />
//         {unreadCount >= 1 && <span className="badge">{unreadCount}</span>}
//       </div>

//       {showNotifications && (
//         <div className="notification-list">
//           <div className="notification-item">
//             <p>Notification 1: Reservation Booked.</p>
//           </div>
//           <div className="notification-item">
//             <p>Notification 2: Reservation Changed.</p>
//           </div>
//         </div>
//       )
//       }
//     </div>
//   );
// };

// export default NotificationBell;
