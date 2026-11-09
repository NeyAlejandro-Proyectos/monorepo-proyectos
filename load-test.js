import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter, Rate, Trend } from 'k6/metrics';

/**
 * KAN-15: Grafana k6 Load & Stress Testing Script for AWS Multi-Instance ALB
 * Execute with:
 *   k6 run load-test.js
 *   k6 run -e TARGET_URL=http://load-balancer-anythink-1307084058.us-east-1.elb.amazonaws.com load-test.js
 */

const bffLatency = new Trend('bff_response_time_ms');
const successRate = new Rate('http_success_rate');
const totalRequests = new Counter('total_monorepo_requests');

const BASE_URL =
  __ENV.TARGET_URL ||
  'http://load-balancer-anythink-1307084058.us-east-1.elb.amazonaws.com';

export const options = {
  stages: [
    { duration: '10s', target: 20 }, // Ramp-up to 20 concurrent Virtual Users (VUs)
    { duration: '20s', target: 50 }, // Stress peak: 50 concurrent VUs hitting the 3 EC2 nodes
    { duration: '10s', target: 0 },  // Ramp-down to 0 VUs
  ],
  thresholds: {
    http_req_duration: ['p(95)<2000'], // 95% of requests must complete under 2000ms
    http_success_rate: ['rate>0.90'],  // At least 90% success rate under heavy load
  },
};

export default function () {
  // 1. Test Health & Load Balancing across the 3 EC2 instances
  const healthRes = http.get(`${BASE_URL}/api/personaje/health`, {
    headers: { Accept: 'application/json, text/plain' },
    timeout: '10s',
  });

  totalRequests.add(1);
  bffLatency.add(healthRes.timings.duration);

  const isOk = check(healthRes, {
    'HTTP status is 200 OK': (r) => r.status === 200,
    'Response body is OK': (r) => r.body && r.body.includes('OK'),
    'Latency under 1000ms': (r) => r.timings.duration < 1000,
  });

  successRate.add(isOk);

  // 2. Test Main Portal Page served by Spring Boot BFF
  const portalRes = http.get(`${BASE_URL}/`, { timeout: '10s' });
  totalRequests.add(1);

  check(portalRes, {
    'Portal index.html loaded (200)': (r) => r.status === 200,
  });

  sleep(0.5);
}
