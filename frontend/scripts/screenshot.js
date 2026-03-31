/**
 * spec-to-pr 用スクリーンショットスクリプト
 *
 * 使い方:
 *   node scripts/screenshot.js [base_url]
 *
 * デフォルト base_url: http://localhost:5173
 */

import { chromium } from '@playwright/test';
import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';

const BASE_URL = process.argv[2] || 'http://localhost:5173';
const __dirname = path.dirname(fileURLToPath(import.meta.url));
const OUT_DIR = path.resolve(__dirname, '../.screenshots');

// スクリーンショットを撮るページ一覧
// App.tsx のルーティングに合わせて追加・変更してください
const PAGES = [
  { path: '/',      name: 'home' },
  { path: '/users', name: 'users' },
];

async function run() {
  if (!fs.existsSync(OUT_DIR)) {
    fs.mkdirSync(OUT_DIR, { recursive: true });
  }

  const browser = await chromium.launch();
  const context = await browser.newContext({
    viewport: { width: 1280, height: 800 },
  });

  const results = [];

  for (const { path: pagePath, name } of PAGES) {
    const page = await context.newPage();
    const url = `${BASE_URL}${pagePath}`;
    try {
      await page.goto(url, { waitUntil: 'networkidle', timeout: 10000 });
      await page.waitForTimeout(500);
      const file = `${name}.png`;
      await page.screenshot({ path: path.join(OUT_DIR, file), fullPage: true });
      results.push({ name, file, url });
      console.log(`✅ ${name}: ${file}`);
    } catch (e) {
      console.error(`❌ ${name} (${url}): ${e.message}`);
    } finally {
      await page.close();
    }
  }

  await browser.close();

  // PR Description 用の Markdown テーブルを出力
  console.log('\n--- PR Description 用マークダウン ---');
  const headers = results.map(r => r.name).join(' | ');
  const images = results.map(r => `![${r.name}](.screenshots/${r.file})`).join(' | ');
  console.log(`| ${headers} |`);
  console.log(`| ${results.map(() => '---').join(' | ')} |`);
  console.log(`| ${images} |`);
}

run().catch(e => {
  console.error(e);
  process.exit(1);
});
